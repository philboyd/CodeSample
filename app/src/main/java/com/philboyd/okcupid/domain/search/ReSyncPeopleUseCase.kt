package com.philboyd.okcupid.domain.search

class ReSyncPeopleUseCase(private val peopleRepository: PeopleRepository) {

    fun execute() = peopleRepository.invalidate()
}
