package com.philboyd.okcupid.di

import com.philboyd.okcupid.data.core.MemoryReactiveStore
import com.philboyd.okcupid.data.search.*
import com.philboyd.okcupid.domain.search.*
import retrofit2.Retrofit

class AppContainer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://okcupid.com")
        .build()

    private val personService = retrofit.create(SearchApiService::class.java)
    private val searchNetworkDataSource = SearchNetworkDataSource(personService)

    private val peopleStore: PeopleStore =
        MemoryReactiveStore<PeopleStoreKey, List<Person>> { PeopleStoreKey }
    private val personRepository: PeopleRepository =
        PeopleDataRepository(peopleStore, searchNetworkDataSource)

    val observeLikedPeopleUseCase =
        ObservePeopleUseCase(
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
