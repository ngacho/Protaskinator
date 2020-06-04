package com.brocodes.wedoit.commonutils

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.wedoit.R
import com.brocodes.wedoit.databinding.TaskListItemBinding
import com.brocodes.wedoit.model.entity.Task
import java.util.*
import kotlin.collections.ArrayList

class TaskListAdapter(private val taskList: List<Task>, private val clickListener: (Task) -> Unit) :
    RecyclerView.Adapter<TaskListAdapter.TaskListItemViewHolder>(), Filterable {

    private var filteredTaskList = ArrayList<Task>()

    init {
        filteredTaskList = taskList as ArrayList<Task>
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val taskListItemBinding = DataBindingUtil.inflate<TaskListItemBinding>(
            layoutInflater,
            R.layout.task_list_item,
            parent,
            false
        )

        return TaskListItemViewHolder(taskListItemBinding)
    }

    override fun getItemCount(): Int = filteredTaskList.size

    override fun onBindViewHolder(holder: TaskListItemViewHolder, position: Int) =
        holder.bind(filteredTaskList[position], clickListener = clickListener)

    fun getTaskAt(position: Int) = filteredTaskList[position]

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val charString = constraint.toString()
            filteredTaskList = if (charString.isEmpty()) taskList as ArrayList<Task>
            else {
                val tempFilteredTaskList = arrayListOf<Task>()
                filteredTaskList.forEach {
                    //search within the task list, add items to it each time a character changes
                    if (it.taskTitle.toLowerCase(Locale.getDefault()).contains(
                            charString.toLowerCase(
                                Locale.getDefault()
                            )
                        ) || it.taskDescription.toLowerCase(Locale.getDefault()).contains(
                            charString.toLowerCase(
                                Locale.getDefault()
                            )
                        )
                    ) {
                        tempFilteredTaskList.add(it)
                    }
                }
                tempFilteredTaskList
            }

            val filterResults = FilterResults()
            filterResults.values = filteredTaskList
            return filterResults
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredTaskList = results?.values as ArrayList<Task>
            notifyDataSetChanged()
        }
    }

    class TaskListItemViewHolder(private val taskListItem: TaskListItemBinding) :
        RecyclerView.ViewHolder(taskListItem.root) {
        fun bind(task: Task, clickListener: (Task) -> Unit) {
            if (task.isComplete) {
                //strike through these items
                taskListItem.taskTitleItem.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                taskListItem.taskDescriptionItem.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                taskListItem.dateValueItem.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                //hide the rest
                taskListItem.priorityLabelItem.visibility = View.GONE
            }
            itemView.setOnClickListener { clickListener(task) }
            taskListItem.task = task
            taskListItem.executePendingBindings()
        }

    }
}