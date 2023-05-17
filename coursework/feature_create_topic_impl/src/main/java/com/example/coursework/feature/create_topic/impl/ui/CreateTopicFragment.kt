package com.example.coursework.feature.create_topic.impl.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.ui.argument
import com.example.core.ui.assistedViewModel
import com.example.coursework.feature.create_topic.api.R
import com.example.coursework.feature.create_topic.api.databinding.FragmentCreateTopicBinding
import com.example.coursework.feature.create_topic.impl.CreateTopicFacade
import com.example.coursework.feature.create_topic.impl.domain.CreateTopicResult
import com.example.coursework.feature.create_topic.impl.ui.elm.CreateTopicEffect
import com.example.coursework.feature.create_topic.impl.ui.elm.CreateTopicEvent
import com.example.coursework.feature.create_topic.impl.ui.elm.CreateTopicState
import com.example.coursework.feature.create_topic.impl.ui.elm.CreateTopicStoreFactory
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import javax.inject.Inject

class CreateTopicFragment : ElmFragment<CreateTopicEvent, CreateTopicEffect, CreateTopicState>(R.layout.fragment_create_topic) {
    @Inject lateinit var storeFactory: CreateTopicStoreFactory
    private val viewModel by assistedViewModel { CreateTopicViewModel(storeFactory) }
    private val binding by viewBinding(FragmentCreateTopicBinding::bind)
    private val streamId: Int by argument(KEY_STREAM_ID)
    private val stream: String by argument(KEY_STREAM)

    override val initEvent by lazy { CreateTopicEvent.Ui.Init(streamId, stream) }

    override fun onAttach(context: Context) {
        CreateTopicFacade.component.inject(this)
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
        binding.btnCreateTopic.setOnClickListener {
            viewModel.clickCreateTopic()
        }
        binding.etTopicName.editText!!.doOnTextChanged { text, _, _, _ ->
            viewModel.updateTopicTitle(text?.toString().orEmpty())
        }
        binding.etFirstMessage.editText!!.doOnTextChanged { text, _, _, _ ->
            viewModel.updateFirstMessage(text?.toString().orEmpty())
        }
    }

    override val storeHolder by lazy {
        LifecycleAwareStoreHolder(lifecycle, viewModel::store)
    }

    override fun render(state: CreateTopicState) {
        renderStream(state.stream)
        renderCreateTopicResult(state.createTopicResult)
    }

    private fun renderStream(stream: String) {
        binding.etStreamName.editText!!.setText(stream)
    }

    private fun renderCreateTopicResult(result: CreateTopicResult?) {
        val resultString = result?.let { getString(it.stringRes) }
        when (result) {
            CreateTopicResult.WAIT_A_BIT -> binding.etTopicName.error = resultString
            CreateTopicResult.NAME_BLANK -> binding.etTopicName.error = resultString
            CreateTopicResult.NAME_OCCUPIED -> binding.etTopicName.error = resultString
            CreateTopicResult.MESSAGE_BLANK -> binding.etFirstMessage.error = resultString
            null -> {
                binding.etTopicName.error = null
                binding.etFirstMessage.error = null
            }
            CreateTopicResult.VALID -> {}
        }
    }

    companion object {
        private const val KEY_STREAM_ID = "KEY_STREAM_ID"
        private const val KEY_STREAM = "KEY_STREAM"

        fun newInstance(streamId: Int, stream: String) = CreateTopicFragment().apply {
            arguments = bundleOf(KEY_STREAM_ID to streamId, KEY_STREAM to stream)
        }
    }
}
