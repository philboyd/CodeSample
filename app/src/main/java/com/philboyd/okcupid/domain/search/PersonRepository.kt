package com.philboyd.okcupid.domain.search

import com.philboyd.okcupid.domain.core.Option
import com.philboyd.okcupid.domain.search.Person
import io.reactivex.Observable

interface PersonRepository {
    fun observeLiked(): Observable<Option<Set<Person>>>
    fun isPersonLiked(id: Int): Boolean
    fun like(person: Person)
    fun unlike(person: Person)
}
