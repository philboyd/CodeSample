package com.philboyd.okcupid.data.search

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface SearchService {

    @GET("matchSample.json")
    fun getMatches() : Single<Response<ApiMatchResponse>>
}
