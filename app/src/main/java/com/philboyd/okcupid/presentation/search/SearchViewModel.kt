package com.philboyd.okcupid.presentation.search

import com.philboyd.okcupid.presentation.core.ViewModel

class SearchViewModel(

) : ViewModel<SearchViewModel.ViewState, SearchViewModel.Action>(ViewState(), update) {

    data class ViewState(
        val number: Int = 0
    )

    sealed class Action {
        object Increment : Action()
    }
}

private val update : (SearchViewModel.ViewState, SearchViewModel.Action) -> SearchViewModel.ViewState = { state, action ->
    when (action) {
        SearchViewModel.Action.Increment -> state
    }
}
