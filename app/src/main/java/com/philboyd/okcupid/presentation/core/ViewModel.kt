package com.philboyd.okcupid.presentation.core

import androidx.databinding.BaseObservable
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

import kotlin.properties.Delegates

abstract class ViewModel<ViewState : Any, Action : Any>(
    initialState: ViewState,
    val update: (ViewState, Action) -> ViewState
) : BaseObservable() {

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
