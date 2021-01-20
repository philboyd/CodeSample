package com.philboyd.okcupid.presentation.search.people

import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyModelWithView
import com.bumptech.glide.Glide
import com.philboyd.okcupid.R
import com.philboyd.okcupid.domain.search.Person
import com.philboyd.okcupid.presentation.core.inflateAs
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.item_person.view.*

data class PersonModel(
    val person: Person,
    val callBack: PersonCallBack
) : EpoxyModelWithView<CardView>() {

    override fun bind(view: CardView) {
        super.bind(view)

        with(view) {
            personStats.text = Phrase.from(context, R.string.person_details)
                .put("age", person.age)
                .put("city", person.city)
                .put("region", person.region)
                .format()

            matchPercentage.text = Phrase.from(context, R.string.person_match_percentage)
                .put("match", person.matchPercentage)
                .format()

            personUsername.text = person.userName

            Glide.with(context)
                .load(person.image)
                .fitCenter()
                .dontAnimate()
                .placeholder(R.color.grey)
                .into(personImage)

            val backgroundColor =
                if (person.isLiked) R.color.people_card_liked else R.color.people_card_not_liked
            setCardBackgroundColor(ContextCompat.getColor(view.context, backgroundColor))

            setOnClickListener { callBack.onPersonPressed(person) }
        }
    }

    override fun buildView(parent: ViewGroup): CardView {
        return parent.inflateAs(R.layout.item_person)
    }
}

interface PersonCallBack {
    fun onPersonPressed(person: Person)
}
