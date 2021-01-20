package com.philboyd.okcupid.data.search

import com.philboyd.okcupid.data.core.syncIfEmpty
import com.philboyd.okcupid.domain.core.*
import com.philboyd.okcupid.domain.search.Person
import com.philboyd.okcupid.domain.search.PeopleRepository
import io.reactivex.Maybe
import io.reactivex.Observable
import remotedata.RemoteData

class PeopleDataRepository(
    private val personStore: PeopleStore,
    private val networkDataSource: SearchNetworkDataSource
) : PeopleRepository {

    override fun observe() : Observable<RemoteData<RemoteError, List<Person>>> =
        personStore.get(PeopleStoreKey)
            .syncIfEmpty(sync())

    override fun sync() : Maybe<RemoteError> =
        networkDataSource.getMatches()
            .doIfSuccess { personStore.store(it) }
            .flatMapMaybe { it.toMaybeError() }

}
