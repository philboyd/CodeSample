package com.philboyd.okcupid.presentation.search.blend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.philboyd.okcupid.App
import com.philboyd.okcupid.R
import com.philboyd.okcupid.domain.search.Person
import com.philboyd.okcupid.presentation.core.attachToLifecycle
import com.philboyd.okcupid.presentation.search.people.PeoplePagedController
import com.philboyd.okcupid.presentation.search.people.PersonCallBack
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_match.*
import remotedata.get

class SpecialBlendFragment :
    Fragment(R.layout.fragment_special_blend),
    PersonCallBack
{

    private lateinit var viewModel: SpecialBlendViewModel
    private val controller = PeoplePagedController(this, emptySet())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appContainer = (requireActivity().application as App).appContainer
        viewModel = SpecialBlendViewModel(
            appContainer.observeLikedPeopleUseCase,
            appContainer.toggleLikedPersonUseCase,
            AndroidSchedulers.mainThread()
        )
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), NUMBER_OF_COLUMNS)
            itemAnimator?.apply {
                moveDuration = 0L
                addDuration = 0L
                removeDuration = 0L
            }
            setController(controller)
        }
        viewModel.stub()
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()

        viewModel.observe()
            .map { it.matches }
            .distinctUntilChanged()
            .subscribe {
                controller.submitList(it.get())
            }
            .attachToLifecycle(this)

        viewModel.observe()
            .map { it.liked }
            .distinctUntilChanged()
            .subscribe {
                controller.likedPeopleIds = it
                controller.requestForcedModelBuild()
            }
            .attachToLifecycle(this)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stub()
    }

    override fun onPersonPressed(person: Person) {
        viewModel.toggleLike(person)
    }

    companion object {
        private const val NUMBER_OF_COLUMNS = 2

        fun newInstance() = SpecialBlendFragment()
    }
}
