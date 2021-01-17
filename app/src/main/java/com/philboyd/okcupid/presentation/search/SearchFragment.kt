package com.philboyd.okcupid.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.philboyd.okcupid.R
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FragmentAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val titleRes = when (position) {
                ChildFragment.SPECIAL_BLEND.ordinal -> R.string.search_tab_blend
                ChildFragment.MATCH_PERCENTAGE.ordinal -> R.string.search_tab_match_percentage
                else -> null
            }

            titleRes?.let { tab.text = requireContext().getText(it) }
        }.attach()
    }

    enum class ChildFragment {
        SPECIAL_BLEND,
        MATCH_PERCENTAGE
        ;

        companion object {
            fun valueOf(value: Int): ChildFragment =
                when (value) {
                    SPECIAL_BLEND.ordinal -> SPECIAL_BLEND
                    MATCH_PERCENTAGE.ordinal -> MATCH_PERCENTAGE
                    else -> throw IllegalArgumentException("Value $value must be in ordinal range of ChildFragment")
                }
        }
    }
}
