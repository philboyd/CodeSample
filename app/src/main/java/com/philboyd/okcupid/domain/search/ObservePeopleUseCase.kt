package com.philboyd.okcupid.domain.search

import com.philboyd.okcupid.domain.core.RemoteError
import io.reactivex.Observable
import remotedata.RemoteData

class ObservePeopleUseCase(private val repository: PeopleRepository) {

    fun execute(): Observable<RemoteData<RemoteError, List<Person>>> =
        repository.observe()
}
