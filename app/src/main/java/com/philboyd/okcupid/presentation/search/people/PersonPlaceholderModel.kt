package com.philboyd.okcupid.presentation.search.people

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyModelWithView
import com.philboyd.okcupid.databinding.ItemPersonBinding

class PersonPlaceholderModel : EpoxyModelWithView<CardView>() {

    private lateinit var binding: ItemPersonBinding

    override fun bind(view: CardView) {
        super.bind(view)

        with(binding.root) {
            isClickable = false
            isFocusable = false
        }
    }

    override fun buildView(parent: ViewGroup): CardView {
        binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context))
        return binding.root
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false
        return true
    }
}
