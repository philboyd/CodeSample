package com.philboyd.okcupid.presentation.search.people

import android.content.Context
import android.util.AttributeSet
import android.widget.ViewFlipper
import androidx.recyclerview.widget.GridLayoutManager
import com.philboyd.okcupid.domain.core.RemoteError
import com.philboyd.okcupid.domain.search.Person
import kotlinx.android.synthetic.main.view_fetch_error.view.*
import kotlinx.android.synthetic.main.view_people.view.*
import remotedata.RemoteData

private const val NUMBER_OF_COLUMNS = 2

class PeopleView : ViewFlipper, PersonCallBack {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private lateinit var callBack: Callback

    private val controller = PeopleController(this)
    private val gridLayoutManager = GridLayoutManager(context, NUMBER_OF_COLUMNS)

    override fun onFinishInflate() {
        super.onFinishInflate()

        recyclerView.apply {
            itemAnimator?.apply {
                moveDuration = 0L
                addDuration = 0L
                removeDuration = 0L
            }
            layoutManager = gridLayoutManager
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        recyclerView.setController(controller)
    }

    fun bind(peopleData: RemoteData<RemoteError, List<Person>>, peopleViewCallback: Callback) {
        callBack = peopleViewCallback

        when (peopleData) {
            RemoteData.NotAsked -> renderNotAsked()
            RemoteData.Loading -> renderLoading()
            is RemoteData.Success -> renderSuccess(peopleData.data)
            is RemoteData.Failure -> renderError(peopleData.error)
        }
    }

    override fun onPersonPressed(person: Person) {
        callBack.personPressed(person)
    }

    private fun renderLoading() {
        displayedChild = State.LOADING.ordinal
    }

    private fun renderNotAsked() {
        displayedChild = State.NOT_ASKED.ordinal
    }

    private fun renderError(error: RemoteError) {
        displayedChild = State.ERROR.ordinal

        retry.setOnClickListener { callBack.retryPressed() }
    }

    private fun renderSuccess(people: List<Person>) {
        displayedChild = State.SUCCESS.ordinal
        controller.setData(people)
    }

    interface Callback {
        fun personPressed(person: Person)
        fun retryPressed()
    }

    private enum class State {
        NOT_ASKED,
        LOADING,
        ERROR,
        SUCCESS,
    }
}
