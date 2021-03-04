package com.philboyd.okcupid.presentation.search.match

import androidx.databinding.Bindable
import com.philboyd.okcupid.domain.core.RemoteError
import com.philboyd.okcupid.domain.search.ObserveMatchedPeopleUseCase
import com.philboyd.okcupid.domain.search.Person
import com.philboyd.okcupid.domain.search.ReSyncPeopleUseCase
import com.philboyd.okcupid.domain.search.ToggleLikedPersonUseCase
import com.philboyd.okcupid.presentation.core.ViewModel
import com.philboyd.okcupid.presentation.search.people.PeopleView
import com.philboyd.okcupid.presentation.search.people.PeopleWrapper
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import remotedata.RemoteData
import remotedata.get

class MatchViewModel(
    private val matchedPeopleUseCase: ObserveMatchedPeopleUseCase,
    private val toggleLikedPersonUseCase: ToggleLikedPersonUseCase,
    private val resyncPeopleUseCase: ReSyncPeopleUseCase,
    private val observerScheduler: Scheduler
) : ViewModel<MatchViewModel.ViewState, MatchViewModel.Action>(
    ViewState(),
    update
), PeopleView.Callback {

    data class ViewState(
        val topMatches: RemoteData<RemoteError, List<Person>> = RemoteData.Loading
    )

    sealed class Action {
        data class TopMatchesReceived(val data: RemoteData<RemoteError, List<Person>>) : Action()
    }

    init {
        this.observe()
            .subscribe {
                notifyChange()
            }
            .addTo(disposables)
    }

    @Bindable
    fun getTopMatches(): PeopleWrapper {
        return this.observe()
            .map { it.topMatches }
            .map { PeopleWrapper(it) }
            .blockingFirst()
    }


    fun start() {
        matchedPeopleUseCase.execute()
            .observeOn(observerScheduler)
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
            .subscribe {
                dispatch(Action.TopMatchesReceived(it))
            }
            .addTo(disposables)
    }

    override fun personPressed(person: Person) {
        toggleLikedPersonUseCase.execute(person)
    }

    override fun retryPressed() {
        resyncPeopleUseCase.execute()
        disposables.clear()
        start()
    }
}

private val update: (MatchViewModel.ViewState, MatchViewModel.Action) -> MatchViewModel.ViewState =
    { state, action ->
        when (action) {
            is MatchViewModel.Action.TopMatchesReceived -> state.copy(
                topMatches = action.data
            )
        }
    }
