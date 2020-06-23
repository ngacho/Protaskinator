package com.brocodes.wedoit.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.brocodes.wedoit.R
import com.brocodes.wedoit.databinding.FragmentOnboardingSwipeactionsBinding

class OnboardingSwipeActionsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val onboardingSwipeactionsBinding =
            DataBindingUtil.inflate<FragmentOnboardingSwipeactionsBinding>(
                inflater,
                R.layout.fragment_onboarding_swipeactions,
                container,
                false
            )
        // Inflate the layout for this fragment
        return onboardingSwipeactionsBinding.root
    }

}