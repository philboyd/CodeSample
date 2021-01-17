package com.philboyd.okcupid.presentation.search.people

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.view_people.view.*

class PeopleView : ConstraintLayout, PersonCallBack {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val controller = PeopleController(this)
    private val layoutManager = GridLayoutManager(context, NUMBER_OF_COLUMNS)

    lateinit var callBack: Callback

    fun bind(peopleViewCallback: Callback) {
        callBack = peopleViewCallback
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        recyclerView.layoutManager = layoutManager
        recyclerView.setController(controller)
    }

    override fun onPersonPressed(userName: String) {
        callBack.personPress(userName)
    }

    interface Callback {
        fun personPress(userName: String)
    }

    companion object {
        private const val NUMBER_OF_COLUMNS = 2
    }
}
