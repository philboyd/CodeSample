package com.philboyd.okcupid.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.philboyd.okcupid.R
import com.philboyd.okcupid.presentation.search.people.PeopleView
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(R.layout.fragment_search),
    PeopleView.Callback {

    private val controller = SearchPageController(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = controller.adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val titleRes = when (position) {
                0 -> R.string.search_tab_blend
                1 -> R.string.search_tab_match_percentage
                else -> null
            }

            titleRes?.let { tab.text = requireContext().getText(it) }
        }.attach()

        controller.setData("data")
    }

    override fun personPress(userName: String) {
        // TODO dispatch event
    }
}