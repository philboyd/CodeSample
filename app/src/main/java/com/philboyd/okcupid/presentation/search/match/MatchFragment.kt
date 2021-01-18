package com.philboyd.okcupid.presentation.search.match

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.philboyd.okcupid.App
import com.philboyd.okcupid.R
import com.philboyd.okcupid.presentation.core.attachToLifecycle
import com.philboyd.okcupid.presentation.search.people.PeopleController
import com.philboyd.okcupid.presentation.search.people.PersonCallBack
import kotlinx.android.synthetic.main.fragment_match.*
import remotedata.RemoteData

class MatchFragment :
    Fragment(R.layout.fragment_match),
    PersonCallBack {
    private lateinit var viewModel: MatchViewModel
    private val controller = PeopleController(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appContainer = (requireActivity().application as App).appContainer
        viewModel = MatchViewModel(
            appContainer.observeMatchedPeopleUseCase,
            appContainer.toggleLikedPersonUseCase
        )

        recyclerView.layoutManager = GridLayoutManager(requireContext(), NUMBER_OF_COLUMNS)
        recyclerView.setController(controller)
    }

    override fun onResume() {
        super.onResume()

        viewModel.observe()
            .map { it.topMatches }
            .distinctUntilChanged()
            .subscribe {
                when (it) {
                    is RemoteData.Success -> controller.setData(it.data)
                    RemoteData.NotAsked,
                    RemoteData.Loading,
                    is RemoteData.Failure -> {
                    }
                }
            }
            .attachToLifecycle(this)
    }

    override fun onPersonPressed(id: Int) {
        viewModel.removeLike(id)
    }

    companion object {
        private const val NUMBER_OF_COLUMNS = 2

        fun newInstance() = MatchFragment()
    }
}