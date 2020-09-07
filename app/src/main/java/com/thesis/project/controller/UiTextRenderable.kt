package com.thesis.project.controller

import android.content.Context
import android.view.MotionEvent
import androidx.core.content.ContextCompat.getColor
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.TransformableNode
import com.google.ar.sceneform.ux.TransformationSystem
import com.thesis.project.R
import com.thesis.project.models.arnote.ArNote
import com.thesis.project.ui.arcamera.ArCameraActivity
import com.thesis.project.ui.main.MainActivity
import me.grantland.widget.AutofitTextView


class UiTextRenderable(context: Context,
                       transformationSystem: TransformationSystem,
                       text: String,
                       size: Float,
                       private var billboarding: Boolean,
                       resolvedArNote: ArNote?) :TransformableNode(transformationSystem), Node.OnTapListener
{
    companion object { var arAutoFitTextView: AutofitTextView? = null }

    private var uiElement = Node()

    private var resolvedArNote : ArNote?
    private lateinit var cameraPosition : Vector3
    private lateinit var uiPosition: Vector3
    private lateinit var direction: Vector3
    private lateinit var lookRotation: Quaternion

    init
    {
        this.uiElement.setParent(this)
        this.uiElement.isEnabled = true
        this.uiElement.localPosition = Vector3(0.0f, size, 0.0f)
        this.resolvedArNote = resolvedArNote

        viewRenderableBuilder(context,text)

        setOnTapListener(this)
    }

    override fun onUpdate(frameTime: FrameTime?)
    {
        if (billboarding)
        {
            scene?.let {
                cameraPosition = it.camera.worldPosition
                uiPosition = uiElement.worldPosition
                direction = Vector3.subtract(cameraPosition, uiPosition)
                direction.y = 0.0f
                lookRotation = Quaternion.lookRotation(direction, Vector3.up())
                uiElement.worldRotation = lookRotation
            }
        }
    }

    private fun spinnerSelector(context: Context)
    {
        val type = getSelectedType()
        when (type)
        {
            "Normal" -> arAutoFitTextViewCustomization(context, R.drawable.rounded_corner_normal, R.color.white)
            "Warning" -> arAutoFitTextViewCustomization(context, R.drawable.rounded_corner_warning, R.color.Black)
            "Urgent" -> arAutoFitTextViewCustomization(context, R.drawable.rounded_corner_urgent, R.color.white)
        }
        resolvedArNote = null
    }

    private fun getSelectedType() : String
    {
        return when(resolvedArNote)
        {
            null -> ArCameraActivity.spinner!!.selectedItem.toString()
            else -> resolvedArNote!!.type
        }
    }

    private fun viewRenderableBuilder(context: Context, text: String)
    {

        ViewRenderable.builder()

            .setView(context, R.layout.label_layout)
            .build()
            .thenAccept { uiRenderable: ViewRenderable ->
                uiRenderable.isShadowCaster = false
                uiRenderable.isShadowReceiver = false
                uiElement.renderable = uiRenderable
                arAutoFitTextView = uiRenderable.view.findViewById(R.id.arAutoFitTextView)
                setArAutoTextViewText(text)
                spinnerSelector(context)
            }.exceptionally { throwable: Throwable? -> throw AssertionError("Could not create ui element",throwable )}
    }

    private fun setArAutoTextViewText(text: String)
    {
        when(resolvedArNote)
        {
            null-> arAutoFitTextView!!.text = text
            else -> arAutoFitTextView!!.text = resolvedArNote!!.text
        }
    }

    private fun arAutoFitTextViewCustomization(context: Context, backgroundlook : Int, color : Int)
    {
        arAutoFitTextView!!.setBackgroundResource(backgroundlook)
        arAutoFitTextView!!.setTextColor(getColor(context,color))
    }
}
