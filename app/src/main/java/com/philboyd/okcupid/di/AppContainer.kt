package com.philboyd.okcupid.di

import com.philboyd.okcupid.data.core.MemoryReactiveStore
import com.philboyd.okcupid.data.search.PersonDataRepository
import com.philboyd.okcupid.domain.core.ReactiveStore
import com.philboyd.okcupid.domain.search.*

class AppContainer {
    private val reactiveStore: ReactiveStore<Int, Person> =
        MemoryReactiveStore<Int, Person> { it.id }
    private val personRepository: PersonRepository =
        PersonDataRepository(reactiveStore)

    val observeLikedPeopleUseCase =
        ObserveLikedPeopleUseCase(
            personRepository
        )
    val observeMatchedPeopleUseCase =
        ObserveMatchedPeopleUseCase(
            observeLikedPeopleUseCase
        )
    val toggleLikedPersonUseCase =
        ToggleLikedPersonUseCase(
            personRepository
        )
}