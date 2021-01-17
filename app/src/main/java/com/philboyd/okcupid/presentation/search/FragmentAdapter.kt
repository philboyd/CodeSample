package com.philboyd.okcupid.presentation.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.philboyd.okcupid.presentation.search.blend.SpecialBlendFragment
import com.philboyd.okcupid.presentation.search.match.MatchFragment

class FragmentAdapter(fragment: SearchFragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = SearchFragment.ChildFragment.values().size

    override fun createFragment(position: Int): Fragment {
        return when (SearchFragment.ChildFragment.valueOf(position)) {
            SearchFragment.ChildFragment.SPECIAL_BLEND -> SpecialBlendFragment.newInstance()
            SearchFragment.ChildFragment.MATCH_PERCENTAGE -> MatchFragment.newInstance()
        }
    }
}
