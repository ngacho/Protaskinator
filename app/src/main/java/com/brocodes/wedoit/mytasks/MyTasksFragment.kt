package com.brocodes.wedoit.mytasks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.wedoit.R
import com.brocodes.wedoit.commonutils.SwipeActionCallback
import com.brocodes.wedoit.commonutils.TaskListAdapter
import com.brocodes.wedoit.databinding.FragmentMyTasksBinding
import com.brocodes.wedoit.mainactivity.MainActivity
import com.brocodes.wedoit.mytasks.viewmodel.MyTasksViewModel
import com.brocodes.wedoit.mytasks.viewmodel.MyTasksViewModelFactory

class MyTasksFragment : Fragment() {

    private lateinit var taskListAdapter: TaskListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //set up the view model
        val repository = (activity as MainActivity).taskRepository
        val myTasksViewModel = ViewModelProvider(
            viewModelStore,
            MyTasksViewModelFactory(repository)
        )
            .get(MyTasksViewModel::class.java)

        val myTasksBinding = DataBindingUtil.inflate<FragmentMyTasksBinding>(
            inflater,
            R.layout.fragment_my_tasks,
            container,
            false
        )


        val myTasksRecyclerView = myTasksBinding.myTasksRecyclerview
        myTasksRecyclerView.apply {
            setHasFixedSize(true)
        }

        myTasksViewModel.allTasks.observe(viewLifecycleOwner, Observer {
            taskListAdapter = TaskListAdapter(it)
            myTasksRecyclerView.adapter = taskListAdapter
        })

        val swipeToDeleteCallback = object : SwipeActionCallback(
            requireContext(),
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.RIGHT) {
                    Log.d("Right swipe", "Item completed")
                } else {
                    Log.d("Task swiped", "Position $direction")
                    val task = taskListAdapter.getTaskAt(viewHolder.adapterPosition)
                    myTasksRecyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                    myTasksViewModel.deleteTask(task)
                }

            }

        }

        //configure right swipe
        swipeToDeleteCallback.rightBG =
            ContextCompat.getColor(requireContext(), R.color.completeTaskColor)
        swipeToDeleteCallback.rightLabel = "Completed"
        swipeToDeleteCallback.rightIcon =
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_complete)

        //configure left swipe
        swipeToDeleteCallback.leftBG =
            ContextCompat.getColor(requireContext(), R.color.deleteTaskColor)
        swipeToDeleteCallback.leftLabel = "Delete"
        swipeToDeleteCallback.leftIcon =
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_delete)

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(myTasksRecyclerView)


        // Inflate the layout for this fragment
        return myTasksBinding.root
    }
}

