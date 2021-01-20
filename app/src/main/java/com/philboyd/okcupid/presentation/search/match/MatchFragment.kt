package com.philboyd.okcupid.presentation.search.match

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.philboyd.okcupid.App
import com.philboyd.okcupid.R
import com.philboyd.okcupid.domain.search.Person
import com.philboyd.okcupid.presentation.core.attachToLifecycle
import com.philboyd.okcupid.presentation.search.people.PeopleView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.view_people.*

class MatchFragment :
    Fragment(R.layout.fragment_match),
    PeopleView.Callback {
    private lateinit var viewModel: MatchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchContainer = (requireActivity().application as App).appContainer.searchContainer
        viewModel = MatchViewModel(
            searchContainer.observeMatchedPeopleUseCase,
            searchContainer.toggleLikedPersonUseCase,
            searchContainer.reSyncPeopleUseCase,
            AndroidSchedulers.mainThread()
        )
    }

    override fun onResume() {
        super.onResume()

        viewModel.start()

        viewModel.observe()
            .map { it.topMatches }
            .distinctUntilChanged()
            .subscribe {
                peopleView.bind(it, this)
            }
            .attachToLifecycle(this)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stop()
    }

    override fun personPressed(person: Person) {
        viewModel.removeLike(person)
    }

    override fun retryPressed() {
        viewModel.retry()
    }

    companion object {
        fun newInstance() = MatchFragment()
    }
}
