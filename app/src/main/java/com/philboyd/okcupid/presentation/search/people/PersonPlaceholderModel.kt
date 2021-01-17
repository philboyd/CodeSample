package com.philboyd.okcupid.presentation.search.people

import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyModelWithView
import com.philboyd.okcupid.R
import com.philboyd.okcupid.presentation.core.inflateAs

class PersonPlaceholderModel : EpoxyModelWithView<CardView>() {

    override fun bind(view: CardView) {
        super.bind(view)

        with(view) {
            isClickable = false
            isFocusable = false
        }
    }

    override fun buildView(parent: ViewGroup): CardView {
        return parent.inflateAs(R.layout.item_person)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false
        return true
    }
}
