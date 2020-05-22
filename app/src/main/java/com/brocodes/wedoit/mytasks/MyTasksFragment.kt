package com.brocodes.wedoit.mytasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.wedoit.R
import com.brocodes.wedoit.commonutils.TaskListAdapter
import com.brocodes.wedoit.databinding.FragmentMyTasksBinding
import com.brocodes.wedoit.databinding.FragmentPriorityTasksBinding
import com.brocodes.wedoit.mainactivity.MainActivity
import com.brocodes.wedoit.mytasks.viewmodel.MyTasksViewModel
import com.brocodes.wedoit.mytasks.viewmodel.MyTasksViewModelFactory

class MyTasksFragment : Fragment() {


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


        val recyclerView = myTasksBinding.myTasksRecyclerview
        recyclerView.apply {
            setHasFixedSize(true)
        }

        myTasksViewModel.allTasks.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = TaskListAdapter(it)
        })


        // Inflate the layout for this fragment
        return myTasksBinding.root
    }
}
