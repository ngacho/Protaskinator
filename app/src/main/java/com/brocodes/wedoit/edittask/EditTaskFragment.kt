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
import com.brocodes.wedoit.mainactivity.MainActivity
import com.brocodes.wedoit.model.entity.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class EditTaskFragment(private var task: Task) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val editTaskViewModel = (activity as MainActivity).mainActivityViewModel

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
            //calendar and priority picker implementations
            cal[Calendar.DAY_OF_MONTH] = datePicker.dayOfMonth
            cal[Calendar.MONTH] = datePicker.month
            cal[Calendar.YEAR] = datePicker.year
            val todayTimeInMillis = Calendar.getInstance().timeInMillis
            val selectedTimeInMillis =
                if ((cal.timeInMillis / (1000 * 3600 * 24)) <= todayTimeInMillis / (1000 * 3600 * 24)) {
                    todayTimeInMillis + (1000 * 3600 * 24 * 7)
                } else {
                    cal.timeInMillis
                }

            val priority = priorityPicker.value

            val taskName: String
            val taskDescription: String
            if (!taskNameEditText.text.toString()
                    .isBlank() && !taskDescriptionEditText.text.toString().isBlank()
            ) {
                taskName = taskNameEditText.text.toString().trim()
                taskDescription = taskDescriptionEditText.text.toString().trim()
            } else if (taskNameEditText.text.isBlank()) {
                taskName = task.taskTitle
                taskDescription = taskDescriptionEditText.text.toString().trim()
            } else if (taskDescriptionEditText.text.isBlank()) {
                taskName = taskNameEditText.text.toString().trim()
                taskDescription = task.taskDescription
            } else {
                taskName = task.taskTitle
                taskDescription = task.taskDescription
            }
            val newTask = Task(
                id = task.id,
                taskTitle = taskName,
                taskDescription = taskDescription,
                priority = priority,
                date = selectedTimeInMillis

            )
            editTaskViewModel.editTask(newTask)

            val saveToast = Toast.makeText(this.context, "Task Saved", Toast.LENGTH_SHORT)
            saveToast.show()
            dismiss()
        }

        cancelButton.setOnClickListener {
            Log.i("Cancel Button", "Cancelled")
            dismiss()
        }

        return editTaskBinding.root
    }
}
