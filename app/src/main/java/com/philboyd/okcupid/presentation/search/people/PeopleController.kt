package com.philboyd.okcupid.presentation.search.people

import com.airbnb.epoxy.TypedEpoxyController
import com.philboyd.okcupid.domain.Person

class PeopleController(
    private val personCallBack: PersonCallBack
) : TypedEpoxyController<List<Person>>() {

    override fun buildModels(data: List<Person>?) {
        data?.forEach {
            PersonModel(it, it.isLiked, personCallBack)
                .id(it.id)
                .addTo(this)
        }
    }
}
