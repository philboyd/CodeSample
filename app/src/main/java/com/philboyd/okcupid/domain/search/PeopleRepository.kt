package com.philboyd.okcupid.domain.search

import com.philboyd.okcupid.domain.core.RemoteError
import io.reactivex.Maybe
import io.reactivex.Observable
import remotedata.RemoteData

interface PeopleRepository {
    fun observe() : Observable<RemoteData<RemoteError, List<Person>>>
    fun sync() : Maybe<RemoteError>
}
