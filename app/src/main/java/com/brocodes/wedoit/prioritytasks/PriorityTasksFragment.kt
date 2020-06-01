package com.brocodes.wedoit.prioritytasks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.wedoit.R
import com.brocodes.wedoit.commonutils.SwipeActionCallBack
import com.brocodes.wedoit.commonutils.TaskListAdapter
import com.brocodes.wedoit.databinding.FragmentPriorityTasksBinding
import com.brocodes.wedoit.edittask.EditTaskFragment
import com.brocodes.wedoit.mainactivity.MainActivity
import com.brocodes.wedoit.model.entity.Task
import com.brocodes.wedoit.prioritytasks.viewmodel.PriorityTasksViewModel
import com.brocodes.wedoit.prioritytasks.viewmodel.PriorityTasksViewModelFactory


class PriorityTasksFragment : Fragment() {

    lateinit var priorityTasksAdapter: TaskListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val priorityTasksRepository = (activity as MainActivity).taskRepository
        val priorityTasksViewModel = ViewModelProvider(
            viewModelStore,
            PriorityTasksViewModelFactory(priorityTasksRepository)
        ).get(PriorityTasksViewModel::class.java)

        val priorityTasksBinding = DataBindingUtil.inflate<FragmentPriorityTasksBinding>(
            inflater,
            R.layout.fragment_priority_tasks,
            container,
            false
        )

        val priorityTasksRecyclerView = priorityTasksBinding.priorityTasksRecyclerview
        priorityTasksRecyclerView.setHasFixedSize(true)

        priorityTasksViewModel.priorityTasks.observe(
            viewLifecycleOwner,
            Observer { priorityTasksList ->
                priorityTasksAdapter = TaskListAdapter(priorityTasksList) {task -> showEditTaskFragment(task) }
                priorityTasksRecyclerView.adapter = priorityTasksAdapter
            })

        val swipeActionsCallBack = object : SwipeActionCallBack(
            requireContext(),
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedTask = priorityTasksAdapter.getTaskAt(viewHolder.adapterPosition)
                if (direction == ItemTouchHelper.RIGHT) {
                    //swipe left to right to complete task
                    priorityTasksViewModel.completeTask(swipedTask)
                    Toast.makeText(context, "Task Completed", Toast.LENGTH_SHORT).show()
                } else {
                    //swipe right to left to delete task
                    priorityTasksViewModel.deleteTask(swipedTask)
                    Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeActionsCallBack)
        itemTouchHelper.attachToRecyclerView(priorityTasksRecyclerView)


        // Inflate the layout for this fragment
        return priorityTasksBinding.root
    }

    private fun showEditTaskFragment(task : Task){
        val bottomSheetFragment = EditTaskFragment(task)
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }

}
