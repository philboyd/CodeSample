package com.philboyd.okcupid.presentation.search.match

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.philboyd.okcupid.App
import com.philboyd.okcupid.R
import com.philboyd.okcupid.domain.search.Person
import com.philboyd.okcupid.presentation.core.attachToLifecycle
import com.philboyd.okcupid.presentation.search.people.PeopleController
import com.philboyd.okcupid.presentation.search.people.PersonCallBack
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_match.*

class MatchFragment :
    Fragment(R.layout.fragment_match),
    PersonCallBack {
    private lateinit var viewModel: MatchViewModel
    private val controller = PeopleController(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchContainer = (requireActivity().application as App).appContainer.searchContainer
        viewModel = MatchViewModel(
            searchContainer.observeMatchedPeopleUseCase,
            searchContainer.toggleLikedPersonUseCase,
            AndroidSchedulers.mainThread()
        )

        recyclerView.layoutManager = GridLayoutManager(requireContext(), NUMBER_OF_COLUMNS)
        recyclerView.setController(controller)
    }

    override fun onResume() {
        super.onResume()

        viewModel.start()

        viewModel.observe()
            .map { it.topMatches }
            .distinctUntilChanged()
            .subscribe {
                controller.setData(it)
            }
            .attachToLifecycle(this)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stop()
    }

    override fun onPersonPressed(person: Person) {
        viewModel.removeLike(person)
    }

    companion object {
        private const val NUMBER_OF_COLUMNS = 2

        fun newInstance() = MatchFragment()
    }
}