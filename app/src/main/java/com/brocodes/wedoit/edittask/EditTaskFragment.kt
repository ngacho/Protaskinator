package com.brocodes.wedoit.edittask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.brocodes.wedoit.R
import com.brocodes.wedoit.databinding.FragmentEditTaskBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditTaskFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Set dialog initial state when shown
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val sheetInternal: View = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            BottomSheetBehavior.from(sheetInternal).state = BottomSheetBehavior.STATE_EXPANDED
        }

        val editTaskBinding = DataBindingUtil.inflate<FragmentEditTaskBinding>(
            inflater,
            R.layout.fragment_edit_task,
            container,
            false
        )



        val taskNameEditText = editTaskBinding.taskTitleEdittext
        val taskDescriptionEditText = editTaskBinding.taskDescriptionEdittext
        val priorityPicker = editTaskBinding.numberPicker
        val saveButton = editTaskBinding.saveButton
        val cancelButton = editTaskBinding.cancelButton

        saveButton.setOnClickListener{
            val saveToast = Toast.makeText(this.context, "Task Saved", Toast.LENGTH_SHORT)
            saveToast.show()
            Log.i("Save Button", "Task Saved! Priority: ${priorityPicker.value}")
            dismiss()
        }

        cancelButton.setOnClickListener {
            Log.i("Cancel Button", "Save Operation Cancelled")
            dismiss()
        }

        return editTaskBinding.root
    }
}
