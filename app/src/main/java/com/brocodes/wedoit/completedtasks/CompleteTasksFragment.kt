package com.brocodes.wedoit.completedtasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.brocodes.wedoit.R
import com.brocodes.wedoit.databinding.FragmentCompletedTasksBinding

class CompleteTasksFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val completeTasksBinding = DataBindingUtil.inflate<FragmentCompletedTasksBinding>(
            inflater,
            R.layout.fragment_completed_tasks,
            container,
            false
        )
        // Inflate the layout for this fragment
        return completeTasksBinding.root
    }
}
