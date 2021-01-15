package com.philboyd.okcupid.presentation.core

import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlin.properties.Delegates

abstract class ViewModel<ViewState : Any, Action : Any>(
    initialState: ViewState,
    val update: (ViewState, Action) -> ViewState
) {

    val disposables = CompositeDisposable()

    private val stateRelay = BehaviorRelay.createDefault(initialState).toSerialized()
    private var state: ViewState by Delegates.observable(initialState) { _, _, newState ->
        stateRelay.accept(newState)
    }

    fun dispatch(action: Action) {
        val currentState = state
        val newState = update(currentState, action)
        state = newState
    }

    fun observe(): Observable<ViewState> = stateRelay.hide()

    open fun stop() {
        disposables.clear()
    }
}