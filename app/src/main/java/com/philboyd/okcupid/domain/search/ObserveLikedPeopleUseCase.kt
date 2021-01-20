package com.philboyd.okcupid.domain.search

import io.reactivex.Observable

class ObserveLikedPeopleUseCase(private val repository: PeopleRepository) {

    fun execute(): Observable<Set<Person>> = TODO()
}
