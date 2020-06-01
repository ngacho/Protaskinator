package com.brocodes.wedoit.edittask

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.brocodes.wedoit.R
import com.brocodes.wedoit.addtask.viewmodel.AddTaskViewModel
import com.brocodes.wedoit.addtask.viewmodel.AddTaskViewModelFactory
import com.brocodes.wedoit.databinding.FragmentEditTaskBinding
import com.brocodes.wedoit.edittask.viewmodel.EditTaskViewModel
import com.brocodes.wedoit.edittask.viewmodel.EditTaskViewModelFactory
import com.brocodes.wedoit.mainactivity.MainActivity
import com.brocodes.wedoit.model.entity.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class EditTaskFragment(private val task: Task) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val repository = (activity as MainActivity).taskRepository

        val editTaskViewModel =
            ViewModelProvider(viewModelStore, EditTaskViewModelFactory(repository))
                .get(EditTaskViewModel::class.java)

        // Set dialog initial state when shown
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val sheetInternal: View =
                bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
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
        val datePicker = editTaskBinding.dateDuePicker
        val saveButton = editTaskBinding.saveButton
        val cancelButton = editTaskBinding.cancelButton

        //changing the date into time in millis to be set in the date picker
        val cal = Calendar.getInstance()
        cal.timeInMillis = task.date

        //updating the edit text fragments based on values in the task
        taskNameEditText.text.append(task.taskTitle)
        taskDescriptionEditText.text.append(task.taskDescription)
        priorityPicker.value = task.priority
        datePicker.updateDate(cal[Calendar.YEAR], cal[Calendar.MONTH], cal[Calendar.DAY_OF_MONTH])

        saveButton.setOnClickListener {
            val saveToast = Toast.makeText(this.context, "Task Saved", Toast.LENGTH_SHORT)
            saveToast.show()
            dismiss()
        }

        cancelButton.setOnClickListener {
            Log.i("Cancel Button", "Save Operation Cancelled")
            dismiss()
        }

        return editTaskBinding.root
    }
}
