package com.philboyd.okcupid.di

class AppContainer {
    private val networkContainer = NetworkContainer()

    val searchContainer = SearchContainer(networkContainer.retrofit)
}
