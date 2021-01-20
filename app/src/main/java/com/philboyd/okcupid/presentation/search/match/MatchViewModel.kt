package com.philboyd.okcupid.presentation.search.match

import com.philboyd.okcupid.domain.core.RemoteError
import com.philboyd.okcupid.domain.search.ObserveMatchedPeopleUseCase
import com.philboyd.okcupid.domain.search.Person
import com.philboyd.okcupid.domain.search.ToggleLikedPersonUseCase
import com.philboyd.okcupid.presentation.core.ViewModel
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import remotedata.RemoteData
import remotedata.get


class MatchViewModel(
    private val matchedPeopleUseCase: ObserveMatchedPeopleUseCase,
    private val toggleLikedPersonUseCase: ToggleLikedPersonUseCase,
    private val observerScheduler: Scheduler
) : ViewModel<MatchViewModel.ViewState, MatchViewModel.Action>(
    ViewState(),
    update
) {

    data class ViewState(
        val topMatches: List<Person> = emptyList()
    )

    sealed class Action {
        data class TopMatchesReceived(val data: RemoteData<RemoteError, List<Person>>) : Action()
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

    fun removeLike(person: Person) {
        toggleLikedPersonUseCase.execute(person)
    }
}

private val update: (MatchViewModel.ViewState, MatchViewModel.Action) -> MatchViewModel.ViewState =
    { state, action ->
        when (action) {
            is MatchViewModel.Action.TopMatchesReceived -> state.copy(
                topMatches = action.data.get() ?: emptyList()
            )
        }
    }
