package com.philboyd.okcupid.di

import com.philboyd.okcupid.data.core.MemoryReactiveStore
import com.philboyd.okcupid.data.search.*
import com.philboyd.okcupid.domain.search.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class AppContainer {
    private val networkContainer = NetworkContainer()

    val searchContainer = SearchContainer(networkContainer.retrofit)
}
