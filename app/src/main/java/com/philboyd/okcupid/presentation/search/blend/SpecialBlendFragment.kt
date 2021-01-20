package com.philboyd.okcupid.presentation.search.blend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.philboyd.okcupid.App
import com.philboyd.okcupid.R
import com.philboyd.okcupid.domain.core.SyncError
import com.philboyd.okcupid.domain.search.Person
import com.philboyd.okcupid.presentation.core.attachToLifecycle
import com.philboyd.okcupid.presentation.search.people.PeopleView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.view_people.*
import remotedata.RemoteData

class SpecialBlendFragment :
    Fragment(R.layout.fragment_special_blend),
    PeopleView.Callback {

    private lateinit var viewModel: SpecialBlendViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchContainer = (requireActivity().application as App).appContainer.searchContainer
        viewModel = SpecialBlendViewModel(
            searchContainer.observePeopleUseCase,
            searchContainer.toggleLikedPersonUseCase,
            searchContainer.reSyncPeopleUseCase,
            AndroidSchedulers.mainThread()
        )
        viewModel.start()
    }

    override fun onResume() {
        super.onResume()

        viewModel.observe()
            .map { it.matches }
            .distinctUntilChanged()
            .subscribe {
                peopleView.bind(it, this)
            }
            .attachToLifecycle(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stop()
    }

    override fun personPressed(person: Person) {
        viewModel.toggleLike(person)
    }

    override fun retryPressed() {
        viewModel.retry()
    }

    companion object {
        fun newInstance() = SpecialBlendFragment()
    }
}
