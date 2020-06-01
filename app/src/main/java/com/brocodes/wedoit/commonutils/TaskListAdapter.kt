package com.brocodes.wedoit.commonutils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.wedoit.R
import com.brocodes.wedoit.databinding.TaskListItemBinding
import com.brocodes.wedoit.model.entity.Task

class TaskListAdapter(private val taskList: List<Task>, private val clickListener: (Task) -> Unit) :
    RecyclerView.Adapter<TaskListAdapter.TaskListItemViewHolder>() {


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

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskListItemViewHolder, position: Int) =
        holder.bind(taskList[position], clickListener = clickListener)

    fun getTaskAt(position: Int) = taskList[position]

    class TaskListItemViewHolder(private val taskListItem: TaskListItemBinding) :
        RecyclerView.ViewHolder(taskListItem.root) {
        fun bind(task: Task, clickListener: (Task) -> Unit) {
            itemView.setOnClickListener { clickListener(task) }
            taskListItem.task = task
            taskListItem.executePendingBindings()
        }

    }
}