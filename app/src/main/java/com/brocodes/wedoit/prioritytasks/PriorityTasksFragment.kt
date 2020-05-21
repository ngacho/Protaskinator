package com.brocodes.wedoit.prioritytasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.brocodes.wedoit.R
import com.brocodes.wedoit.databinding.FragmentPriorityTasksBinding


class PriorityTasksFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val priorityTasksBinding = DataBindingUtil.inflate<FragmentPriorityTasksBinding>(
            inflater,
            R.layout.fragment_priority_tasks,
            container,
            false
        )


        // Inflate the layout for this fragment
        return priorityTasksBinding.root
    }

}
