package com.brocodes.wedoit.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.brocodes.wedoit.R
import com.brocodes.wedoit.databinding.FragmentAddTaskBinding
import com.brocodes.wedoit.mainactivity.MainActivity
import com.brocodes.wedoit.model.entity.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class AddTaskFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val addTaskViewModel = (activity as MainActivity).mainActivityViewModel

        // Set dialog initial state when shown
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val sheetInternal: View =
                bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            BottomSheetBehavior.from(sheetInternal).state = BottomSheetBehavior.STATE_EXPANDED
        }

        val addTaskBinding = DataBindingUtil.inflate<FragmentAddTaskBinding>(
            inflater,
            R.layout.fragment_add_task,
            container,
            false
        )


        val taskNameEditText = addTaskBinding.taskTitleEdittext
        val taskDescriptionEditText = addTaskBinding.taskDescriptionEdittext
        val priorityPicker = addTaskBinding.numberPicker
        val saveButton = addTaskBinding.saveButton
        val cancelButton = addTaskBinding.cancelButton
        val datePicker = addTaskBinding.dateDuePicker


        saveButton.setOnClickListener {
            val cal = Calendar.getInstance()

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
            //Fetch task if both task title and task name are not the empty
            if (!taskNameEditText.text.toString()
                    .isBlank() && !taskDescriptionEditText.text.toString().isBlank()
            ) {

                val taskName = taskNameEditText.text.toString().trim()
                val taskDescription = taskDescriptionEditText.text.toString().trim()

                val task = Task(
                    taskTitle = taskName,
                    taskDescription = taskDescription,
                    priority = priority,
                    date = selectedTimeInMillis

                )
                addTaskViewModel.addTask(task)
                Toast.makeText(this.context, "Task Saved", Toast.LENGTH_SHORT).show()
                dismiss()
            } else if (taskNameEditText.text.toString().isBlank()) {
                //Error is task name is empty
                taskNameEditText.requestFocus()
                Toast.makeText(this.context, "Please Name your task", Toast.LENGTH_SHORT).show()
            } else if (taskDescriptionEditText.text.toString().isBlank()) {
                //error if task description is empty
                taskNameEditText.requestFocus()
                Toast.makeText(this.context, "Please Describe your task", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        return addTaskBinding.root
    }


}