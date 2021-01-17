package com.philboyd.okcupid.data

import com.philboyd.okcupid.domain.Person
import com.philboyd.okcupid.domain.PersonRepository
import com.philboyd.okcupid.domain.core.Option
import com.philboyd.okcupid.domain.core.ReactiveStore
import io.reactivex.Observable

class PersonDataRepository(
    private val likedPersonStore: ReactiveStore<Int, Person>
) : PersonRepository {

    override fun observeLiked(): Observable<Option<Set<Person>>> =
        likedPersonStore.getAll()

    override fun isPersonLiked(id: Int): Boolean =
        likedPersonStore.get(id).blockingFirst().isSome()

    override fun like(person: Person) {
        likedPersonStore.store(person)
    }

    override fun unlike(person: Person) {
        likedPersonStore.clear(person.id)
    }
}
