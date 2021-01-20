package com.philboyd.okcupid.domain.core

import io.reactivex.Maybe
import io.reactivex.Single
import remotedata.RemoteData
import remotedata.mapError
import retrofit2.Response

fun <T : Any> Response<T>.toRemoteData(): RemoteData<RemoteError, T> =
    if (isSuccessful) {
        RemoteData.succeed(body()!!)
    } else {
        RemoteData.fail(HttpError(code(), message()))
    }

inline fun <A : Any> attempt(f: () -> A): RemoteData<Throwable, A> = try {
    RemoteData.succeed(f())
} catch (e: Exception) {
    RemoteData.fail(e)
}

inline fun <A : Any> attemptTransform(f: () -> A): RemoteData<RemoteError, A> = attempt { f() }
    .mapError {
        when (it) {
            is ParsingError -> it
            else -> ParsingError(it)
        }
    }

inline fun <A : Any> Single<RemoteData<RemoteError, A>>.doIfSuccess(crossinline f: (A) -> Unit): Single<RemoteData<RemoteError, A>> =
    doOnSuccess {
        if (it is RemoteData.Success) {
            f(it.data)
        }
    }

fun <E : Any, A : Any> RemoteData<E, A>.toMaybeError(): Maybe<E> = when (this) {
    RemoteData.NotAsked,
    RemoteData.Loading,
    is RemoteData.Success -> Maybe.empty()
    is RemoteData.Failure -> Maybe.just(error)
}
