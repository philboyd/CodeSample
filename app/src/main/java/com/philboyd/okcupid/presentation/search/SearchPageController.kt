package com.philboyd.okcupid.presentation.search

import com.airbnb.epoxy.TypedEpoxyController
import com.philboyd.okcupid.presentation.search.people.PeopleView

class SearchPageController(
    private val peopleViewCallback: PeopleView.Callback
) : TypedEpoxyController<String>() {

    override fun buildModels(data: String?) {
        SearchPageModel(peopleViewCallback)
            .id(ID_SPECIAL_BLEND)
            .addTo(this)

        SearchPageModel(peopleViewCallback)
            .id(ID_MATCH_PERCENTAGE)
            .addTo(this)
    }

    companion object {
        private const val ID_SPECIAL_BLEND = "id_special_blend"
        private const val ID_MATCH_PERCENTAGE = "id_match_percentage"
    }
}