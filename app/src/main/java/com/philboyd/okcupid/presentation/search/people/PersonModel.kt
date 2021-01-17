package com.philboyd.okcupid.presentation.search.people

import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyModelWithView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.philboyd.okcupid.R
import com.philboyd.okcupid.domain.Person
import com.philboyd.okcupid.presentation.core.inflateAs
import kotlinx.android.synthetic.main.item_person.view.*

private const val FADE_DURATION = 200

data class PersonModel(
    val person: Person,
    val callBack: PersonCallBack
) : EpoxyModelWithView<CardView>() {

    override fun bind(view: CardView) {
        super.bind(view)

        // TODO use string resources, add Phrase
        with(view) {
            personStats.text = "${person.age} ${person.city}, ${person.region}"
            matchPercentage.text = "${person.matchPercentage}% Match"
            personUsername.text = person.userName

            Glide.with(context)
                .load(person.image)
                .transition(DrawableTransitionOptions.withCrossFade(FADE_DURATION))
                .placeholder(R.drawable.ic_person_placeholder)
                .into(personImage)

            val backgroundColor =
                if (person.isLiked) R.color.people_card_liked else R.color.people_card_not_liked
            setCardBackgroundColor(ContextCompat.getColor(view.context, backgroundColor))

            setOnClickListener { callBack.onPersonPressed(person.id) }
        }
    }

    override fun buildView(parent: ViewGroup): CardView {
        return parent.inflateAs(R.layout.item_person)
    }
}

interface PersonCallBack {
    fun onPersonPressed(id: Int)
}
