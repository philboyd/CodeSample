package com.philboyd.okcupid.data

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.philboyd.okcupid.domain.Option
import com.philboyd.okcupid.domain.ReactiveStore
import com.philboyd.okcupid.domain.toOption
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class MemoryReactiveStore<K : Any, V : Any>(
    private val cache: ConcurrentMap<K, V> = ConcurrentHashMap(),
    private inline val keyForItem: (V) -> K
) : ReactiveStore<K, V> {

    private val itemsRelay = PublishRelay.create<Option<Set<V>>>()
        .toSerialized()

    private val itemRelays = ConcurrentHashMap<K, Relay<Option<V>>>()

    override fun store(item: V) {
        val key = keyForItem(item)
        cache[key] = item

        getOrCreatePublisher(key).accept(item.toOption())
        itemsRelay.accept(getAllItems())
    }

    @Suppress("CheckResult")
    override fun store(items: Collection<V>) {
        Observable.just(items)
            .subscribeOn(Schedulers.computation())
            .subscribe {
                val keyValue = it.associateBy(keyForItem)
                cache.putAll(keyValue)
                itemsRelay.accept(getAllItems())

                updateExistingPublishers()
            }
    }

    override fun update(key: K, update: (V?) -> V) {
        val newValue = update(cache[key])
        store(newValue)
    }

    override fun get(key: K): Observable<Option<V>> =
        Observable.defer {
            val item = cache[key].toOption()
            getOrCreatePublisher(key).startWith(item)
        }

    override fun getAll(): Observable<Option<Set<V>>> =
        Observable.defer {
            val allItems = getAllItems()
            itemsRelay.startWith(allItems)
        }

    override fun clear() {
        cache.clear()
        itemsRelay.accept(getAllItems())
        updateExistingPublishers()
    }

    override fun clear(key: K) {
        cache.remove(key)
        itemsRelay.accept(getAllItems())
        updateExistingPublishers()
    }

    private fun getAllItems(): Option<Set<V>> = cache.values.toSet()
        .toOption()
        .filter { it.isNotEmpty() }

    @Synchronized
    private fun getOrCreatePublisher(key: K): Relay<Option<V>> =
        itemRelays.getOrPut(key) {
            PublishRelay.create<Option<V>>().toSerialized()
        }

    @Synchronized
    private fun updateExistingPublishers() {
        val keys = itemRelays.keys().toList().toSet()
        keys.forEach {
            val item = cache[it].toOption()
            updateItemPublisher(it, item)
        }
    }

    private fun updateItemPublisher(key: K, item: Option<V>) {
        val publisher = itemRelays[key].toOption()
        when (publisher) {
            is Option.None -> {
            }
            is Option.Some -> {
                publisher.value.accept(item)
            }
        }
    }
}
