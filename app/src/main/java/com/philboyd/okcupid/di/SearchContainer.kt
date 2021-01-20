package com.philboyd.okcupid.di

import com.philboyd.okcupid.data.core.MemoryReactiveStore
import com.philboyd.okcupid.data.search.*
import com.philboyd.okcupid.domain.search.*
import retrofit2.Retrofit

class SearchContainer(retrofit: Retrofit) {
    private val personService = retrofit.create(SearchApiService::class.java)
    private val searchNetworkDataSource = SearchNetworkDataSource(personService)

    private val peopleStore: PeopleStore =
        MemoryReactiveStore<PeopleStoreKey, List<Person>> { PeopleStoreKey }
    private val personRepository: PeopleRepository =
        PeopleDataRepository(peopleStore, searchNetworkDataSource)

    val observePeopleUseCase =
        ObservePeopleUseCase(
            personRepository
        )
    val observeMatchedPeopleUseCase =
        ObserveMatchedPeopleUseCase(
            observePeopleUseCase
        )
    val toggleLikedPersonUseCase =
        ToggleLikedPersonUseCase(
            personRepository
        )
}