package com.brocodes.protaskinator.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.brocodes.protaskinator.R
import com.brocodes.protaskinator.databinding.FragmentOnboardingPriorityDefinitionsBinding

class OnboardingPriorityDefinitionsFragment : Fragment() {


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