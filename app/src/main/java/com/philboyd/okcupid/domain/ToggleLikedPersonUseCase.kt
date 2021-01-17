package com.philboyd.okcupid.domain

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
