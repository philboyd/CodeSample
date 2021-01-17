package com.philboyd.okcupid.domain

import com.philboyd.okcupid.domain.core.orNull
import io.reactivex.Observable

class ObserveLikedPeopleUseCase(private val repository: PersonRepository) {

    fun execute(): Observable<Set<Person>> =
        repository.observeLiked()
            .map { it.orNull() ?: emptySet() }
}
