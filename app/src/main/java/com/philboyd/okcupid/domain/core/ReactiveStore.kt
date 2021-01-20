package com.philboyd.okcupid.domain.core

import io.reactivex.Observable

interface ReactiveStore<K : Any, V : Any> {

    fun store(item: V)

    fun store(items: Collection<V>)

    fun update(key: K, update: (V?) -> V)

    fun get(key: K): Observable<Option<V>>

    fun getAll(): Observable<Option<Set<V>>>

    fun clear()

    fun clear(key: K)
}
