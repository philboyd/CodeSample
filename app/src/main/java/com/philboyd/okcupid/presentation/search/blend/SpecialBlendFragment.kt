package com.philboyd.okcupid.presentation.search.blend

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.philboyd.okcupid.App
import com.philboyd.okcupid.R
import com.philboyd.okcupid.databinding.FragmentMatchBinding
import com.philboyd.okcupid.databinding.FragmentSpecialBlendBinding
import com.philboyd.okcupid.domain.search.Person
import com.philboyd.okcupid.presentation.core.attachToLifecycle
import com.philboyd.okcupid.presentation.search.people.PeopleView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.view_people.*

class SpecialBlendFragment :
    Fragment(R.layout.fragment_special_blend) {

    private lateinit var binding: FragmentSpecialBlendBinding
    private lateinit var viewModel: SpecialBlendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val searchContainer = (requireActivity().application as App).appContainer.searchContainer
        viewModel = SpecialBlendViewModel(
            searchContainer.observePeopleUseCase,
            searchContainer.toggleLikedPersonUseCase,
            searchContainer.reSyncPeopleUseCase,
            AndroidSchedulers.mainThread()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpecialBlendBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stop()
    }

    companion object {
        fun newInstance() = SpecialBlendFragment()
    }
}
