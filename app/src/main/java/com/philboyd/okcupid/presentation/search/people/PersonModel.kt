package com.philboyd.okcupid.presentation.search.people

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyModelWithView
import com.philboyd.okcupid.R
import com.philboyd.okcupid.databinding.ItemPersonBinding
import com.philboyd.okcupid.domain.Person

data class PersonModel(
    val person: Person,
    val callBack: PersonCallBack
) : EpoxyModelWithView<CardView>() {

    private lateinit var binding: ItemPersonBinding

    override fun bind(view: CardView) {
        super.bind(view)

        // TODO use string resources, add Phrase
        // TODO load image
        binding.personStats.text = "${person.age} ${person.city}, ${person.region}"
        binding.matchPercentage.text = "${person.matchPercentage}% Match"
        binding.personUsername.text = person.userName

        val backgroundColor =
            if (person.isLiked) R.color.people_card_liked else R.color.people_card_not_liked
        binding.root.setCardBackgroundColor(ContextCompat.getColor(view.context, backgroundColor))

        binding.root.setOnClickListener { callBack.onPersonPressed(person.userName) }
    }

    override fun buildView(parent: ViewGroup): CardView {
        binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context))
        return binding.root
    }
}

interface PersonCallBack {
    fun onPersonPressed(userName: String)
}
