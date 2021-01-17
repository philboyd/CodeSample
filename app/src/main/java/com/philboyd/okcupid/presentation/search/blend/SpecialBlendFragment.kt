package com.philboyd.okcupid.presentation.search.blend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.philboyd.okcupid.R
import com.philboyd.okcupid.presentation.core.attachToLifecycle
import com.philboyd.okcupid.presentation.search.people.PeoplePagedController
import com.philboyd.okcupid.presentation.search.people.PersonCallBack
import kotlinx.android.synthetic.main.fragment_match.*
import remotedata.get

class SpecialBlendFragment :
    Fragment(R.layout.fragment_special_blend),
    PersonCallBack
{

    private lateinit var viewModel: SpecialBlendViewModel
    private val controller = PeoplePagedController(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), NUMBER_OF_COLUMNS)
        recyclerView.setController(controller)
        viewModel = SpecialBlendViewModel()
        viewModel.start()
    }

    override fun onResume() {
        super.onResume()

        viewModel.observe()
            .map { it.matches }
            .distinctUntilChanged()
            .subscribe {
                controller.submitList(it.get())
            }
            .attachToLifecycle(this)

    }

    override fun onPersonPressed(id: Int) {
        viewModel.toggleLike(id)
    }

    companion object {
        private const val NUMBER_OF_COLUMNS = 2

        fun newInstance() = SpecialBlendFragment()
    }
}
