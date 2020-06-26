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

class TaskListAdapter(
    private val originalTaskList: List<Task>,
    private val clickListener: (Task) -> Unit
) :
    RecyclerView.Adapter<TaskListAdapter.TaskListItemViewHolder>(), Filterable {

    private var filteredTaskList: List<Task> = originalTaskList


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


    override fun getFilter() = taskFilter

    private val taskFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val taskFilterResults = FilterResults()
            val searchQuery = constraint.toString()
            filteredTaskList = if (searchQuery.isEmpty()) {
                originalTaskList
            } else {
                val temporaryFilteredTaskList = mutableListOf<Task>()
                originalTaskList.forEach {
                    if (it.taskTitle.toLowerCase(Locale.getDefault()).contains(
                            searchQuery.toLowerCase(
                                Locale.getDefault()
                            )
                        ) || it.taskDescription.toLowerCase(Locale.getDefault()).contains(
                            searchQuery.toLowerCase(
                                Locale.getDefault()
                            )
                        )
                    ) {
                        temporaryFilteredTaskList.add(it)
                    }
                }
                temporaryFilteredTaskList
            }
            taskFilterResults.values = filteredTaskList

            return taskFilterResults
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredTaskList = results?.values as List<Task>
            this@TaskListAdapter.notifyDataSetChanged()
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