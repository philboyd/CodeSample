package com.philboyd.okcupid.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.philboyd.okcupid.R

class SearchFragment : Fragment(R.layout.fragment_search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}