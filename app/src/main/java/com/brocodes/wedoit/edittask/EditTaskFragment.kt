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
import com.brocodes.wedoit.notification.AlarmScheduler
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vivekkaushik.datepicker.OnDateSelectedListener
import java.util.*
import kotlin.math.abs

class EditTaskFragment : BottomSheetDialogFragment() {


    private lateinit var task: Task
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //pass task as bundle to avoid crashing
        if (arguments != null)
            task = requireArguments().getSerializable("edit_task") as Task
    }

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

        val taskNameEditText = editTaskBinding.tilTaskTitle
        val taskDescriptionEditText = editTaskBinding.tilTaskDescription
        val priorityPicker = editTaskBinding.numberPicker
        val saveButton = editTaskBinding.saveButton
        val cancelButton = editTaskBinding.cancelButton
        //updating the edit text fragments based on values in the task
        taskNameEditText.editText?.text?.append(task.taskTitle)
        taskDescriptionEditText.editText?.text?.append(task.taskDescription)
        priorityPicker.value = task.priority

        //set up date picker
        val datePicker = editTaskBinding.dateDuePicker
        val taskDueDate = Calendar.getInstance(Locale.getDefault())
        //changing the date into time in millis to be set in the date picker
        taskDueDate.timeInMillis = task.date
        datePicker.setInitialDate(
            taskDueDate[Calendar.YEAR],
            taskDueDate[Calendar.MONTH],
            taskDueDate[Calendar.DAY_OF_MONTH]
        )
        val currentTime = Calendar.getInstance(Locale.getDefault())
        val dates = mutableListOf<Date>()
        if (currentTime.timeInMillis > taskDueDate.timeInMillis) {
            dates.add(taskDueDate.time)
            dates.add(currentTime.time)
            val daysBetween = java.util.concurrent.TimeUnit.MILLISECONDS.toDays(
                abs(currentTime.timeInMillis - taskDueDate.timeInMillis)
            )
            for (days in 0 until daysBetween) {
                currentTime.add(Calendar.DAY_OF_YEAR, -1)
                dates.add(currentTime.time)
            }
        }
        datePicker.deactivateDates(dates.toTypedArray())
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
            ) = Toast.makeText(
                requireContext(),
                "Please use another date.",
                Toast.LENGTH_SHORT
            ).show()

        })

        saveButton.setOnClickListener {
            Log.d("save button", "Save button clicked")

            val priority = priorityPicker.value

            val taskName: String
            val taskDescription: String
            if (!taskNameEditText.editText?.text?.toString()?.isBlank()!!
                && !taskDescriptionEditText.editText?.text?.toString()?.isBlank()!!
            ) {
                taskName = taskNameEditText.editText?.text.toString().trim()
                taskDescription = taskDescriptionEditText.editText?.text.toString().trim()
            } else if (taskNameEditText.editText?.text?.toString()?.isBlank()!!) {
                taskName = task.taskTitle
                taskDescription = taskDescriptionEditText.editText?.text.toString().trim()
            } else if (taskDescriptionEditText.editText?.text?.toString()?.isBlank()!!) {
                taskName = taskNameEditText.editText?.text.toString().trim()
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
                date = taskDueDate.timeInMillis

            )
            if (newTask.date < Calendar.getInstance().timeInMillis && task.date != newTask.date) {
                AlarmScheduler.cancelAlarm(requireContext(), newTask)
                AlarmScheduler.setAlarm(requireContext(), newTask, newTask.id)
            }
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
