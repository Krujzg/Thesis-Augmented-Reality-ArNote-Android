package com.thesis.project.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.LinearLayout.*
import android.widget.LinearLayout.LayoutParams.*
import androidx.fragment.app.DialogFragment
import com.thesis.project.controller.OkListener


/** A DialogFragment for the Resolve Dialog Box.  */
class ResolveDialogFragment : DialogFragment() {

    private var okListener: OkListener? = null
    private var shortCodeField: EditText? = null
    /** Sets a listener that is invoked when the OK button_resolve on this dialog is pressed.  */
    fun setOkListener(okListener: OkListener) {this.okListener = okListener }

    /**
     * Creates a simple layout for the dialog. This contains a single user-editable text field whose
     * input type is retricted to numbers only, for simplicity.
     */
    private val dialogLayout: LinearLayout
        get()
        {
            val context: Context = context!!
            val layout = LinearLayout(context)
            shortCodeField = EditText(context)
            shortCodeField!!.inputType = InputType.TYPE_CLASS_NUMBER
            shortCodeField!!.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            shortCodeField!!.filters = arrayOf<InputFilter>(LengthFilter(8))
            layout.addView(shortCodeField)
            layout.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            return layout
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder
            .setView(dialogLayout)
            .setTitle("Resolve Anchor")
            .setPositiveButton("OK")
            { dialog: DialogInterface?, which: Int ->
                val shortCodeText = shortCodeField!!.text
                if (okListener != null && shortCodeText != null && shortCodeText.isNotEmpty())
                { // Invoke the callback with the current checked item.
                    okListener!!.onOkPressed(shortCodeText.toString())
                }
            }
            .setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int -> }
        return builder.create()
    }
}

