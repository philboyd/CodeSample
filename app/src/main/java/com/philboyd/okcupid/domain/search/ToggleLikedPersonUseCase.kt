package com.philboyd.okcupid.domain.search

class ToggleLikedPersonUseCase(
    private val personRepository: PeopleRepository
) {
    fun execute(person: Person) {
        personRepository.toggleLike(person)
    }
}
