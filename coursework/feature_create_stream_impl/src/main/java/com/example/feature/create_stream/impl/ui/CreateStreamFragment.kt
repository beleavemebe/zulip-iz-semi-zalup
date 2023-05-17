package com.example.feature.create_stream.impl.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.ui.assistedViewModel
import com.example.feature.create_stream.impl.CreateStreamFacade
import com.example.feature.create_stream.impl.R
import com.example.feature.create_stream.impl.databinding.FragmentCreateStreamBinding
import com.example.feature.create_stream.impl.domain.IsStreamNameValid
import com.example.feature.create_stream.impl.ui.elm.CreateStreamEffect
import com.example.feature.create_stream.impl.ui.elm.CreateStreamEvent
import com.example.feature.create_stream.impl.ui.elm.CreateStreamState
import com.example.feature.create_stream.impl.ui.elm.CreateStreamStoreFactory
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import javax.inject.Inject

class CreateStreamFragment : ElmFragment<CreateStreamEvent, CreateStreamEffect, CreateStreamState>(R.layout.fragment_create_stream) {
    @Inject lateinit var storeFactory: CreateStreamStoreFactory
    private val viewModel by assistedViewModel { CreateStreamViewModel(storeFactory) }
    private val binding by viewBinding(FragmentCreateStreamBinding::bind)

    override val initEvent = CreateStreamEvent.Ui.Init

    override fun onAttach(context: Context) {
        CreateStreamFacade.component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.clickGoBack()
        }
        binding.btnCreateStream.setOnClickListener {
            viewModel.clickCreateStream()
        }
        binding.etStreamName.editText!!.doOnTextChanged { text, _, _, _ ->
            viewModel.updateStreamTitle(text?.toString().orEmpty())
        }
    }

    override val storeHolder by lazy {
        LifecycleAwareStoreHolder(lifecycle, viewModel::store)
    }

    override fun render(state: CreateStreamState) {
        if (state.isStreamNameValid != IsStreamNameValid.VALID) {
            binding.etStreamName.error = state.isStreamNameValid?.let {
                requireActivity().getString(it.stringRes)
            }
        }
    }
}
