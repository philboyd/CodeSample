package com.philboyd.okcupid.domain.search

import com.philboyd.okcupid.domain.core.RemoteError
import io.reactivex.Observable
import remotedata.RemoteData
import remotedata.success

class ObserveMatchedPeopleUseCase(
    private val observePeopleUseCase: ObservePeopleUseCase
) {

    fun execute(): Observable<RemoteData<RemoteError, List<Person>>> =
        observePeopleUseCase.execute()
            .map { it.mapToTopMatches() }

    private fun RemoteData<RemoteError, List<Person>>.mapToTopMatches(): RemoteData<RemoteError, List<Person>> =
        when (this) {
            is RemoteData.Success -> this.data.getTopMatches().success()
            else -> this
        }

    private fun List<Person>.getTopMatches(): List<Person> =
        this.filter { it.isLiked }
            .sortedByDescending { it.matchPercentage }
            .take(TOP_MATCHES_COUNT)

    companion object {
        private const val TOP_MATCHES_COUNT = 6
    }
}
