package com.thesis.project.ui.arcamera

import android.R.layout.simple_spinner_dropdown_item
import android.R.layout.simple_spinner_item
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter.createFromResource
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.ar.core.Anchor
import com.google.ar.core.CameraConfigFilter
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.ux.ArFragment
import com.thesis.project.R
import com.thesis.project.R.array.spinner_choices
import com.thesis.project.controller.OkListener
import com.thesis.project.controller.UiTextRenderable
import com.thesis.project.models.enums.AppAnchorState
import com.thesis.project.models.enums.AppAnchorState.*
import com.thesis.project.repository.firebase.StorageManager
import com.thesis.project.repository.firebase.interfaces.ShortCodeListener
import com.thesis.project.util.ResolveDialogFragment
import com.thesis.project.util.SnackbarHelper
import kotlinx.android.synthetic.main.activity_arcamera.*

class ArCameraActivity : AppCompatActivity(), OkListener {

    companion object{ var spinner: Spinner? =  null }

    private var snackbarHelper = SnackbarHelper()
    private var cloudAnchor : Anchor? = null
    private var appAnchorState = NONE
    private lateinit var fragment : CustomArFragment
    private lateinit var storageManager: StorageManager
    private lateinit var anchorNode : AnchorNode
    private lateinit var resolvedAnchor : Anchor
    private lateinit var newAnchor: Anchor
    private lateinit var  cloudState : Anchor.CloudAnchorState
    private lateinit var dialog : ResolveDialogFragment
    private lateinit var cameraConfigFilter: CameraConfigFilter

    private var shortCode: Int? = null
    private var edittextWantedTextEdit : EditText? = null
    private var wantedText : String? = null
    private var node: UiTextRenderable? = null
    private lateinit var clearButton: Button
    private lateinit var resolveButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arcamera)

        storageManager =
            StorageManager(this)
        fragmentSetup()
        fragmentSetOnTapArPlaneListener()
        showFilterDialog()
        setting_button.setOnClickListener{ showFilterDialog() }
        back_button.setOnClickListener{ onBackPressed() }
    }

    private fun fragmentSetup()
    {
        fragment =(supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as CustomArFragment?)!!
        fragment.arSceneView.scene.addOnUpdateListener(this::onUpdateFrame)
        fragment.planeDiscoveryController.hide()
    }

    private fun spinnerDataUpload()
    {
        createFromResource(this, spinner_choices, simple_spinner_item)
            .also { adapter -> adapter.setDropDownViewResource(simple_spinner_dropdown_item)
                spinner!!.adapter = adapter }
    }

    private fun resolveButtonSetOnClickListener()
    {
        resolveButton.setOnClickListener(object : View.OnClickListener
        {
            override fun onClick(view: View?) {
                if (cloudAnchor != null)
                {
                    snackbarHelper.showMessageWithDismiss(this@ArCameraActivity, "Please clear Anchor")
                    return
                }
                dialog = ResolveDialogFragment()
                dialog.setOkListener(this@ArCameraActivity)
                dialog.show(supportFragmentManager, "Resolve")
            }
        })
    }

    private fun fragmentSetOnTapArPlaneListener()
    {
        fragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent: MotionEvent? ->

            if (appAnchorState != NONE ) {return@setOnTapArPlaneListener}
            newAnchor =fragment.arSceneView.session!!.hostCloudAnchor(hitResult.createAnchor())
            setCloudAnchor(newAnchor)
            appAnchorState = HOSTING
            snackbarHelper.showMessage(this, "Now hosting anchor...")
            addNodeToScene(fragment, cloudAnchor!!)
        }
    }

    private fun setCloudAnchor(newAnchor: Anchor?)
    {
        if (cloudAnchor != null) {cloudAnchor!!.detach()}
        cloudAnchor = newAnchor
        appAnchorState = NONE
    }

    private fun addNodeToScene(fragment: ArFragment, anchor: Anchor)
    {
        anchorNode = AnchorNode(anchor)
        wantedText = edittextWantedTextEdit!!.text.toString()
        if (wantedText.isNullOrEmpty()){wantedText = "Missing text....."}
        node = UiTextRenderable(this,fragment.transformationSystem,wantedText!!,0.3f,true)
        node!!.setParent(anchorNode)
        fragment.arSceneView.scene.addChild(anchorNode)
        node!!.select()
    }

    private fun onUpdateFrame(frameTime: FrameTime) {checkUpdatedAnchor()}

    @Synchronized
    private fun checkUpdatedAnchor()
    {
        if (appAnchorState != HOSTING && appAnchorState != RESOLVING) {return}
        cloudState = cloudAnchor!!.cloudAnchorState
        when(appAnchorState)
        {
            HOSTING ->
            {
                if (cloudState.isError){ afterCloudAnchorStateCheck(NONE,"Error hosting anchor.. ") }
                else if (cloudState == Anchor.CloudAnchorState.SUCCESS){ appAnchorState_Hosting_cloudAnchorState_Success()}
            }
            RESOLVING ->
            {
                if (cloudState.isError){ afterCloudAnchorStateCheck(NONE,"Error resolving anchor.. ")}
                else if (cloudState == Anchor.CloudAnchorState.SUCCESS){ afterCloudAnchorStateCheck(
                    RESOLVED,"Anchor resolved successfully")}
            }
        }
    }

    private fun getCloudAnchorId(shortCode: Int)
    {
        storageManager.getCloudAnchorID(shortCode)
        {
                cloudAnchorId ->
            resolvedAnchor = fragment.arSceneView.session!!.resolveCloudAnchor(cloudAnchorId)
            setCloudAnchor(resolvedAnchor)
            addNodeToScene(fragment,cloudAnchor!!)
            snackbarHelper.showMessage(this, "Now Resolving Anchor...")
            appAnchorState = RESOLVING
        }
    }

    private fun appAnchorState_Hosting_cloudAnchorState_Success()
    {
        storageManager.nextShortCode(object : ShortCodeListener
        {
            override fun onShortCodeAvailable(shortCode: Int?)
            {
                if (shortCode == null)
                {
                    snackbarHelper.showMessageWithDismiss(this@ArCameraActivity, "Could not get shortCode")
                    return
                }
                storageManager.storeUsingShortCode(shortCode, cloudAnchor!!.cloudAnchorId)
                snackbarHelper.showMessageWithDismiss(this@ArCameraActivity,"Anchor hosted! Cloud Short Code: $shortCode")
                Toast.makeText(applicationContext,"Anchor hosted! Cloud Short Code: $shortCode", Toast.LENGTH_SHORT).show()
            }
        })
        appAnchorState = HOSTED
    }

    private fun afterCloudAnchorStateCheck(_appAnchorState: AppAnchorState, message : String)
    {
        snackbarHelper.showMessageWithDismiss(this@ArCameraActivity, message + cloudState)
        appAnchorState = _appAnchorState
    }

    private fun showFilterDialog(){

        val materialDialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.layout_filter)

        edittextWantedTextEdit = materialDialog.findViewById(R.id.WantedTextEdit)
        spinner = materialDialog.findViewById<AppCompatSpinner>(R.id.spinnerSelection)
        clearButton = materialDialog.findViewById(R.id.clear_button)
        resolveButton = materialDialog.findViewById(R.id.resolve_button)

        spinnerDataUpload()

        clearButton.setOnClickListener { setCloudAnchor(null) }

        resolveButtonSetOnClickListener()

        materialDialog.findViewById<TextView>(R.id.positive_button).setOnClickListener{ materialDialog.dismiss() }
        materialDialog.findViewById<TextView>(R.id.negative_button).setOnClickListener { materialDialog.dismiss() }
        materialDialog.show()
    }

    override fun onOkPressed(dialogValue: String)
    {
        shortCode = dialogValue.toInt()
        getCloudAnchorId(shortCode!!)
    }
}