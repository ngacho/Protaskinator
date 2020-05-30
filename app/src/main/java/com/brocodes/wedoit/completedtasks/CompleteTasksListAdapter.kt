package com.brocodes.wedoit.completedtasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.wedoit.R
import com.brocodes.wedoit.databinding.CompleteTaskListItemBinding
import com.brocodes.wedoit.databinding.TaskListItemBinding
import com.brocodes.wedoit.model.entity.Task

class CompleteTasksListAdapter(private val completeTasks: List<Task>) :
    RecyclerView.Adapter<CompleteTasksListAdapter.CompleteTaskItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompleteTaskItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val completeTaskItemBinding = DataBindingUtil.inflate<CompleteTaskListItemBinding>(
            layoutInflater,
            R.layout.complete_task_list_item,
            parent,
            false
        )

        return CompleteTaskItemViewHolder(completeTaskItemBinding)
    }

    override fun getItemCount(): Int = completeTasks.size

    override fun onBindViewHolder(holder: CompleteTaskItemViewHolder, position: Int) =
        holder.bind(task = completeTasks[position])

    fun getTaskAt(position: Int) = completeTasks[position]


    class CompleteTaskItemViewHolder(private val completeTaskListItemBinding: CompleteTaskListItemBinding) :
        RecyclerView.ViewHolder(completeTaskListItemBinding.root) {

        fun bind(task: Task) {
            completeTaskListItemBinding.task = task
            completeTaskListItemBinding.executePendingBindings()
        }

    }
}