package com.brocodes.wedoit.prioritytasks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.brocodes.wedoit.R
import com.brocodes.wedoit.commonutils.TaskListAdapter
import com.brocodes.wedoit.databinding.FragmentPriorityTasksBinding
import com.brocodes.wedoit.mainactivity.MainActivity
import com.brocodes.wedoit.prioritytasks.viewmodel.PriorityTasksViewModel
import com.brocodes.wedoit.prioritytasks.viewmodel.PriorityTasksViewModelFactory


class PriorityTasksFragment : Fragment() {

    lateinit var priorityTasksAdapter : TaskListAdapter
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

        priorityTasksViewModel.priorityTasks.observe(viewLifecycleOwner, Observer {priorityTasksList ->
            priorityTasksList.forEach {
                Log.d("Priority Tasks", "Task ${it.taskTitle} of Priorrity ${it.priority}")
            }
            priorityTasksAdapter = TaskListAdapter(priorityTasksList)
            priorityTasksRecyclerView.adapter = priorityTasksAdapter
        })


        // Inflate the layout for this fragment
        return priorityTasksBinding.root
    }

}
