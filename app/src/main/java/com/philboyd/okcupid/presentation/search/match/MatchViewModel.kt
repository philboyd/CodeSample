package com.philboyd.okcupid.presentation.search.match

import com.philboyd.okcupid.domain.ObserveMatchedPeopleUseCase
import com.philboyd.okcupid.domain.Person
import com.philboyd.okcupid.domain.ToggleLikedPersonUseCase
import com.philboyd.okcupid.presentation.core.ViewModel
import remotedata.RemoteData
import remotedata.success


class MatchViewModel(
    private val matchedPeopleUseCase: ObserveMatchedPeopleUseCase,
    private val toggleLikedPersonUseCase: ToggleLikedPersonUseCase
) : ViewModel<MatchViewModel.ViewState, MatchViewModel.Action>(
    ViewState(),
    update
) {

    data class ViewState(
        val topMatches: RemoteData<Nothing, List<Person>> = RemoteData.Loading
    )

    sealed class Action {
        data class TopMatchesReceived(val data: List<Person>) : Action()
    }

    fun removeLike(id: Int) {

    }
}

private val update: (MatchViewModel.ViewState, MatchViewModel.Action) -> MatchViewModel.ViewState =
    { state, action ->
        when (action) {
            is MatchViewModel.Action.TopMatchesReceived -> state.copy(topMatches = action.data.success())
        }
    }
