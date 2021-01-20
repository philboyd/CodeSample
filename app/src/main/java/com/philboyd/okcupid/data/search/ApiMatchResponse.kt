package com.philboyd.okcupid.data.search

import com.philboyd.okcupid.domain.core.RemoteError
import com.philboyd.okcupid.domain.core.attemptTransform
import com.philboyd.okcupid.domain.search.Person
import remotedata.RemoteData

data class ApiMatchResponse(
    val data: List<ApiPerson>? = null,
) {
    data class ApiPerson(
        val userid: String? = null,
        val username: String? = null,
        val age: Int? = null,
        val city_name: String? = null,
        val state_code: String? = null,
        val match: Int? = null,
        val liked: Boolean? = null,
        val photo: ApiPhoto? = null
    ) {
        data class ApiPhoto(
            val thumb_paths: ApiThumbPaths? = null
        ) {
            data class ApiThumbPaths(
                val desktop_match: String? = null
            )
        }
    }
}

fun ApiMatchResponse.toDomain(): RemoteData<RemoteError, List<Person>> = attemptTransform {
    this.data!!.map { it.toDomain() }
}

private fun ApiMatchResponse.ApiPerson.toDomain(): Person =
    Person(
        id = userid!!,
        userName = username!!,
        age = age!!,
        city = city_name!!,
        region = state_code!!,
        matchPercentage = match!!.calculateMatchPercentage(),
        isLiked = liked!!,
        image = photo!!.thumb_paths!!.desktop_match!!
    )

private const val MATCH_PERCENTAGE_DENOMINATOR = 10000

private fun Int.calculateMatchPercentage() = (this * 100) / MATCH_PERCENTAGE_DENOMINATOR
