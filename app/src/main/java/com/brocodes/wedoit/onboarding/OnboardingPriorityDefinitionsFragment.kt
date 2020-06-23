package com.brocodes.wedoit.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import com.brocodes.wedoit.R
import com.brocodes.wedoit.databinding.FragmentOnboardingPriorityDefinitionsBinding
import com.brocodes.wedoit.databinding.FragmentPriorityTasksBinding

class OnboardingPriorityDefinitionsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val priorityDefinitionsBinding =
            DataBindingUtil.inflate<FragmentOnboardingPriorityDefinitionsBinding>(
                inflater,
                R.layout.fragment_onboarding_priority_definitions, container, false
            )


        return priorityDefinitionsBinding.root
    }

}