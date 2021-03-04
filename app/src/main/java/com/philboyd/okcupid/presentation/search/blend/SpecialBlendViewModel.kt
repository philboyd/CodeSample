package com.philboyd.okcupid.presentation.search.blend

import androidx.databinding.Bindable
import com.philboyd.okcupid.domain.core.RemoteError
import com.philboyd.okcupid.domain.search.ObservePeopleUseCase
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

typealias MatchesData = RemoteData<RemoteError, List<Person>>

class SpecialBlendViewModel(
    private val personUseCase: ObservePeopleUseCase,
    private val toggleLikedPersonUseCase: ToggleLikedPersonUseCase,
    private val resyncPeopleUseCase: ReSyncPeopleUseCase,
    private val observerScheduler: Scheduler
) : ViewModel<SpecialBlendViewModel.ViewState, SpecialBlendViewModel.Action>(
    ViewState(),
    update
), PeopleView.Callback {

    data class ViewState(
        val matches: MatchesData = RemoteData.Loading,
    )

    sealed class Action {
        data class MatchDataReceived(val data: MatchesData) : Action()
    }

    init {
        this.observe()
            .subscribe {
                notifyChange()
            }
            .addTo(disposables)
    }

    @Bindable
    fun getMatches(): PeopleWrapper {
        return this.observe()
            .map { it.matches }
            .map { PeopleWrapper(it) }
            .blockingFirst()
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

    override fun personPressed(person: Person) {
        toggleLikedPersonUseCase.execute(person)
    }

    override fun retryPressed() {
        resyncPeopleUseCase.execute()
        disposables.clear()
        start()
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
