package com.philboyd.okcupid.presentation.search

import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyModelWithView
import com.philboyd.okcupid.R
import com.philboyd.okcupid.presentation.core.inflateAs
import com.philboyd.okcupid.presentation.search.people.PeopleView

data class SearchPageModel(
    private val peopleViewCallback: PeopleView.Callback
) : EpoxyModelWithView<PeopleView>() {

    override fun bind(view: PeopleView) {
        super.bind(view)
        view.bind(peopleViewCallback)
    }

    override fun buildView(parent: ViewGroup): PeopleView {
        return parent.inflateAs(R.layout.view_people)
    }

}
