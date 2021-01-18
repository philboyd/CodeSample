package com.philboyd.okcupid.domain.search

import com.philboyd.okcupid.domain.search.Person
import com.philboyd.okcupid.domain.search.PersonRepository

class ToggleLikedPersonUseCase(
    private val personRepository: PersonRepository
) {
    fun execute(person: Person) {
        when (personRepository.isPersonLiked(person.id)) {
            true -> personRepository.unlike(person)
            false -> personRepository.like(person)
        }
    }
}
