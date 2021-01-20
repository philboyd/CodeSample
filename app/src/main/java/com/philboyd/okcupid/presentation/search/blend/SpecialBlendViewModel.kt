package com.philboyd.okcupid.presentation.search.blend

import com.philboyd.okcupid.domain.core.RemoteError
import com.philboyd.okcupid.domain.search.ObservePeopleUseCase
import com.philboyd.okcupid.domain.search.Person
import com.philboyd.okcupid.domain.search.ToggleLikedPersonUseCase
import com.philboyd.okcupid.presentation.core.ViewModel
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import remotedata.RemoteData

typealias MatchesData = RemoteData<RemoteError, List<Person>>

class SpecialBlendViewModel(
    private val personUseCase: ObservePeopleUseCase,
    private val toggleLikedPersonUseCase: ToggleLikedPersonUseCase,
    private val observerScheduler: Scheduler
) : ViewModel<SpecialBlendViewModel.ViewState, SpecialBlendViewModel.Action>(
    ViewState(),
    update
) {

    data class ViewState(
        val matches: MatchesData = RemoteData.Loading,
    )

    sealed class Action {
        data class MatchDataReceived(val data: MatchesData) : Action()
    }

    fun start() {
        personUseCase.execute()
            .observeOn(observerScheduler)
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
            .subscribe {
                dispatch(Action.MatchDataReceived(it))
            }
            .addTo(disposables)
    }

    fun toggleLike(person: Person) {
        toggleLikedPersonUseCase.execute(person)
    }
}

private val update: (SpecialBlendViewModel.ViewState, SpecialBlendViewModel.Action) -> SpecialBlendViewModel.ViewState =
    { state, action ->
        when (action) {
            is SpecialBlendViewModel.Action.MatchDataReceived -> {
                state.copy(matches = action.data)
            }
        }
    }
