package com.brocodes.wedoit.duetasks

import android.os.Bundle
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
import com.brocodes.wedoit.databinding.FragmentDueTasksBinding
import com.brocodes.wedoit.duetasks.viewmodel.DueTasksViewModel
import com.brocodes.wedoit.duetasks.viewmodel.DueTasksViewModelFactory
import com.brocodes.wedoit.mainactivity.MainActivity

class DueTasksFragment : Fragment() {

    private lateinit var dueTasksListAdapter: TaskListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dueTasksRepository = (activity as MainActivity).taskRepository
        val dueTasksViewModel =
            ViewModelProvider(viewModelStore, DueTasksViewModelFactory(dueTasksRepository))
                .get(DueTasksViewModel::class.java)

        val dueTasksBinding = DataBindingUtil.inflate<FragmentDueTasksBinding>(
            inflater,
            R.layout.fragment_due_tasks,
            container,
            false
        )

        val dueTasksRecyclerView = dueTasksBinding.dueTasksRecyclerview
        dueTasksRecyclerView.setHasFixedSize(true)
        dueTasksViewModel.allDueTasks.observe(viewLifecycleOwner, Observer {
            dueTasksListAdapter = TaskListAdapter(it)
            dueTasksRecyclerView.adapter = dueTasksListAdapter
        })

        val swipeActionCallBack = object :
            SwipeActionCallBack(requireContext(), 0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val selectedTask = dueTasksListAdapter.getTaskAt(viewHolder.adapterPosition)
                if(direction == ItemTouchHelper.RIGHT){
                    dueTasksViewModel.completeTask(selectedTask)
                    Toast.makeText(context, "Task Marked Incomplete", Toast.LENGTH_SHORT).show()
                }else{
                    dueTasksViewModel.deleteTask(selectedTask)
                    Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
                }
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeActionCallBack)
        itemTouchHelper.attachToRecyclerView(dueTasksRecyclerView)



        // Inflate the layout for this fragment
        return dueTasksBinding.root
    }
}
