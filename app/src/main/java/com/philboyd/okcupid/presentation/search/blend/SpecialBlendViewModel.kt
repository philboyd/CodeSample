package com.philboyd.okcupid.presentation.search.blend

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import androidx.paging.RxPagedListBuilder
import com.philboyd.okcupid.domain.search.ObserveLikedPeopleUseCase
import com.philboyd.okcupid.domain.search.Person
import com.philboyd.okcupid.domain.search.ToggleLikedPersonUseCase
import com.philboyd.okcupid.presentation.core.ViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import remotedata.RemoteData

typealias MatchesData = RemoteData<Throwable, PagedList<Person>>

class SpecialBlendViewModel(
    private val likedPersonUseCase: ObserveLikedPeopleUseCase,
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
