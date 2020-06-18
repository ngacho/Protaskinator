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

        val taskNameEditText = editTaskBinding.taskTitleEdittext
        val taskDescriptionEditText = editTaskBinding.taskDescriptionEdittext
        val priorityPicker = editTaskBinding.numberPicker
        val saveButton = editTaskBinding.saveButton
        val cancelButton = editTaskBinding.cancelButton
        //updating the edit text fragments based on values in the task
        taskNameEditText.text.append(task.taskTitle)
        taskDescriptionEditText.text.append(task.taskDescription)
        priorityPicker.value = task.priority

        //set up date picker
        val datePicker = editTaskBinding.dateDuePicker
        val cal = Calendar.getInstance(Locale.getDefault())
        //changing the date into time in millis to be set in the date picker
        cal.timeInMillis = task.date
        datePicker.setInitialDate(
            cal[Calendar.YEAR],
            cal[Calendar.MONTH],
            cal[Calendar.DAY_OF_MONTH]
        )
        val currentTime = Calendar.getInstance(Locale.getDefault())
        val dates = mutableListOf<Date>()
        if (currentTime.timeInMillis > cal.timeInMillis) {
            dates.add(cal.time)
            dates.add(currentTime.time)
            val daysBetween = java.util.concurrent.TimeUnit.MILLISECONDS.toDays(
                abs(currentTime.timeInMillis - cal.timeInMillis)
            )
            for (days in 0 until daysBetween) {
                currentTime.add(Calendar.DAY_OF_YEAR, -1)
                dates.add(currentTime.time)
            }
        }
        datePicker.deactivateDates(dates.toTypedArray())
        datePicker.setOnDateSelectedListener(object : OnDateSelectedListener {
            override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                cal[Calendar.DAY_OF_MONTH] = day
                cal[Calendar.HOUR_OF_DAY] = 6
                cal[Calendar.MINUTE] = 0
                cal[Calendar.SECOND] = 0
                cal[Calendar.MONTH] = month
                cal[Calendar.YEAR] = year
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
                date = cal.timeInMillis

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
