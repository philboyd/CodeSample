package com.philboyd.okcupid.di

import com.philboyd.okcupid.data.MemoryReactiveStore
import com.philboyd.okcupid.data.PersonDataRepository
import com.philboyd.okcupid.domain.*
import com.philboyd.okcupid.domain.core.ReactiveStore

class AppContainer {
    private val reactiveStore: ReactiveStore<Int, Person> = MemoryReactiveStore<Int, Person> { it.id }
    private val personRepository: PersonRepository = PersonDataRepository(reactiveStore)

    val observeLikedPeopleUseCase = ObserveLikedPeopleUseCase(personRepository)
    val observeMatchedPeopleUseCase = ObserveMatchedPeopleUseCase(observeLikedPeopleUseCase)
    val toggleLikedPersonUseCase = ToggleLikedPersonUseCase(personRepository)
}