package com.philboyd.okcupid.data.core

import com.philboyd.okcupid.domain.core.Option
import com.philboyd.okcupid.domain.core.RemoteError
import com.philboyd.okcupid.domain.core.SyncError
import com.philboyd.okcupid.domain.core.toRemoteData
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.MaybeSubject
import remotedata.RemoteData

fun <A : Any> Observable<Option<A>>.syncIfEmpty(fetch: Maybe<RemoteError>): Observable<RemoteData<RemoteError, A>> =
    syncIfEmpty(fetch) { SyncError }

inline fun <E : Any, A : Any> Observable<Option<A>>.syncIfEmpty(
    fetch: Maybe<E>,
    crossinline errorConverter: (Throwable) -> E
): Observable<RemoteData<E, A>> {
    val errorEmitter = MaybeSubject.create<RemoteData<E, A>>()

    return map { it.toRemoteData<E, A>(emptyValue = RemoteData.Loading) }
        .mergeWith(errorEmitter)
        .doOnNext {
            if (it.isLoading()) {
                fetch.subscribeOn(Schedulers.io()).subscribe(
                    { apiError -> errorEmitter.onSuccess(RemoteData.fail(apiError)) },
                    { ioError -> errorEmitter.onSuccess(RemoteData.fail(errorConverter(ioError))) },
                    { errorEmitter.onComplete() }
                )
            }
        }
}
