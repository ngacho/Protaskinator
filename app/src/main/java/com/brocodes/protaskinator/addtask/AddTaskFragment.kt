package com.brocodes.protaskinator.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.brocodes.protaskinator.R
import com.brocodes.protaskinator.databinding.FragmentAddTaskBinding
import com.brocodes.protaskinator.mainactivity.MainActivity
import com.brocodes.protaskinator.model.entity.Task
import com.brocodes.protaskinator.notification.AlarmScheduler
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vivekkaushik.datepicker.OnDateSelectedListener
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


        val taskNameEditText = addTaskBinding.tilTaskTitle
        val taskDescriptionEditText = addTaskBinding.tilTaskDescription
        val priorityPicker = addTaskBinding.numberPicker
        val saveButton = addTaskBinding.saveButton
        val cancelButton = addTaskBinding.cancelButton
        //setting up the date picker
        val datePicker = addTaskBinding.dateDuePicker
        val taskDueDate = Calendar.getInstance(Locale.getDefault())
        taskDueDate.add(Calendar.DAY_OF_YEAR, 1)
        datePicker.setInitialDate(
            taskDueDate[Calendar.YEAR],
            taskDueDate[Calendar.MONTH],
            taskDueDate[Calendar.DAY_OF_MONTH]
        )
        //setting listener here bc it doesnt work in the button
        datePicker.setOnDateSelectedListener(object : OnDateSelectedListener {
            override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                taskDueDate[Calendar.DAY_OF_MONTH] = day
                taskDueDate[Calendar.HOUR_OF_DAY] = 6
                taskDueDate[Calendar.MINUTE] = 0
                taskDueDate[Calendar.SECOND] = 0
                taskDueDate[Calendar.MONTH] = month
                taskDueDate[Calendar.YEAR] = year
            }

            override fun onDisabledDateSelected(
                year: Int,
                month: Int,
                day: Int,
                dayOfWeek: Int,
                isDisabled: Boolean
            ) = Toast.makeText(requireContext(), "Invalid Date Selected", Toast.LENGTH_SHORT).show()

        })

        saveButton.setOnClickListener {

            val priority = priorityPicker.value
            //Fetch task if both task title and task name are not the empty
            if (!taskNameEditText.editText?.text.toString()
                    .isBlank() && !taskDescriptionEditText.editText?.text.toString().isBlank()
            ) {

                val taskName = taskNameEditText.editText?.text.toString().trim()
                val taskDescription = taskDescriptionEditText.editText?.text.toString().trim()

                val task = Task(
                    taskTitle = taskName,
                    taskDescription = taskDescription,
                    priority = priority,
                    date = taskDueDate.timeInMillis
                )
                val requestCode = addTaskViewModel.addTask(task)
                AlarmScheduler.setAlarm(requireContext(), task, requestCode.toInt())
                Toast.makeText(this.context, "Task Saved", Toast.LENGTH_SHORT).show()
                dismiss()
            } else if (taskNameEditText.editText?.text.toString().isBlank()) {
                //Error is task name is empty
                taskNameEditText.requestFocus()
                Toast.makeText(this.context, "Please Name your task", Toast.LENGTH_SHORT).show()
            } else if (taskDescriptionEditText.editText?.text.toString().isBlank()) {
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