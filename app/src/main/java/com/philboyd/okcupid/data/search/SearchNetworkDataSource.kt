package com.philboyd.okcupid.data.search

import com.philboyd.okcupid.domain.core.RemoteError
import com.philboyd.okcupid.domain.core.toRemoteData
import com.philboyd.okcupid.domain.search.Person
import io.reactivex.Single
import remotedata.RemoteData
import remotedata.flatMap

class SearchNetworkDataSource(private val service: SearchApiService) {

    fun getMatches(): Single<RemoteData<RemoteError, List<Person>>> =
        service.getMatches()
            .map { it.toRemoteData() }
            .map { result -> result.flatMap { it.toDomain() } }
}
