package com.brocodes.wedoit.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.brocodes.wedoit.R
import com.brocodes.wedoit.databinding.FragmentOnboardingDialogueBinding
import com.brocodes.wedoit.mainactivity.MainActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout

class OnboardingDialogue : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val fragmentOnboardingDialogueBinding =
            DataBindingUtil.inflate<FragmentOnboardingDialogueBinding>(
                inflater,
                R.layout.fragment_onboarding_dialogue,
                container,
                false
            )


        // Inflate the layout for this fragment
        return fragmentOnboardingDialogueBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = (requireActivity() as MainActivity).prefs


        val finishButton: MaterialButton = view.findViewById(R.id.finish_btn)

        val onboardingFragmentsAdapter = object : FragmentStatePagerAdapter(
            childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getItem(position: Int): Fragment = when (position) {
                0 -> OnboardingWelcomeFragment()
                1 -> OnboardingSwipeActionsFragment()
                2 -> OnboardingPriorityDefinitionsFragment()
                else -> throw IllegalStateException("Invalid position, $position")
            }

            override fun getCount(): Int = 3
        }

        val viewPager: ViewPager = view.findViewById(R.id.onboarding_view_pager)
        viewPager.adapter = onboardingFragmentsAdapter
        val tabs: TabLayout = view.findViewById(R.id.tab_dots)
        tabs.setupWithViewPager(viewPager)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> Unit
                    1 -> {
                        if (finishButton.visibility == View.VISIBLE)
                            finishButton.visibility = View.INVISIBLE
                    }
                    2 -> finishButton.visibility = View.VISIBLE
                }

            }
        })


        finishButton.setOnClickListener {
            prefs.isFirstTime = false
            dismiss()
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimations
    }
}