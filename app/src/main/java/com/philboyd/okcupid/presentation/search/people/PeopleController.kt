package com.philboyd.okcupid.presentation.search.people

import com.airbnb.epoxy.TypedEpoxyController
import com.philboyd.okcupid.domain.search.Person

class PeopleController(
    private val personCallBack: PersonCallBack
) : TypedEpoxyController<List<Person>>() {

    override fun buildModels(data: List<Person>?) {
        data?.forEach {
            PersonModel(it, personCallBack)
                .id(it.id)
                .addTo(this)
        }
    }
}
