package com.philboyd.okcupid.domain

import io.reactivex.Observable

class ObserveMatchedPeopleUseCase(
    private val observeLikedPeopleUseCase: ObserveLikedPeopleUseCase
) {

    fun execute(): Observable<List<Person>> =
        observeLikedPeopleUseCase.execute()
            .map { it.toList() }
            .map { it.sortedBy { it.matchPercentage } }
            .map { it.take(TOP_MATCHES_COUNT) }


    companion object {
        private const val TOP_MATCHES_COUNT = 6
    }
}