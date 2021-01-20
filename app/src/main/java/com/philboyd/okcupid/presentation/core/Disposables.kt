package com.philboyd.okcupid.presentation.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.Disposable

fun Disposable.attachToLifecycle(owner: LifecycleOwner) {
    owner.lifecycle.addObserver(LifecycleDisposable(this))
}

private class LifecycleDisposable(disposable: Disposable) : LifecycleObserver, Disposable by disposable {

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (!isDisposed) {
            dispose()
        }
    }
}
