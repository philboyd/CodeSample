package com.philboyd.okcupid.data.search

import com.philboyd.okcupid.domain.core.ReactiveStore
import com.philboyd.okcupid.domain.search.Person

object PeopleStoreKey

typealias PeopleStore = ReactiveStore<PeopleStoreKey, List<Person>>
