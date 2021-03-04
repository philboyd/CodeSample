package com.philboyd.okcupid.presentation.search.match

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.philboyd.okcupid.App
import com.philboyd.okcupid.R
import com.philboyd.okcupid.databinding.FragmentMatchBinding
import com.philboyd.okcupid.domain.search.Person
import com.philboyd.okcupid.presentation.core.attachToLifecycle
import com.philboyd.okcupid.presentation.search.people.PeopleView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.view_people.*

class MatchFragment :
    Fragment(R.layout.fragment_match) {
    private lateinit var binding: FragmentMatchBinding
    private lateinit var viewModel: MatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val searchContainer = (requireActivity().application as App).appContainer.searchContainer
        viewModel = MatchViewModel(
            searchContainer.observeMatchedPeopleUseCase,
            searchContainer.toggleLikedPersonUseCase,
            searchContainer.reSyncPeopleUseCase,
            AndroidSchedulers.mainThread()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.start()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stop()
    }

    companion object {
        fun newInstance() = MatchFragment()
    }
}
