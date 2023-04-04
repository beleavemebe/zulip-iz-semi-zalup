package com.example.coursework.chat.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.ui.argument
import com.example.core.ui.assistedViewModel
import com.example.coursework.chat.ui.model.MessageUi
import com.example.coursework.chat.ui.recycler.TopicViewHolderFactory
import com.example.coursework.core.utils.collectWhenStarted
import com.example.feature_chat.R
import com.example.feature_chat.databinding.FragmentTopicBinding
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.tinkoff.mobile.tech.ti_recycler.adapters.AsyncTiAdapter
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import ru.tinkoff.mobile.tech.ti_recycler.base.diff.ViewTypedDiffCallback
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.TiRecyclerCoroutines

class TopicFragment : Fragment(R.layout.fragment_topic) {
    private val stream: Int by argument(KEY_STREAM)
    private val topic: String by argument(KEY_TOPIC)

    private val binding by viewBinding(FragmentTopicBinding::bind)
    private val viewModel by assistedViewModel {
        TopicViewModel(stream, topic)
    }

    private val factory by lazy {
        TopicViewHolderFactory(viewModel::sendOrRevokeReaction, ::pickReactionForMessage)
    }

    private val recycler by lazy {
        TiRecyclerCoroutines(binding.rvChat, AsyncTiAdapter(factory, ViewTypedDiffCallback()))
    }

    private val scrollToBottomObserver by lazy { ScrollToBottomOnItemsAdded() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEditText()
        initSendButton()
        observeState(initRecycler())
    }

    private fun initEditText() {
        binding.etMessageInput.doOnTextChanged { text, _, _, _ ->
            viewModel.setMessageInput(text?.toString().orEmpty())
        }
    }

    private fun initSendButton() {
        binding.ibSend.setOnClickListener {
            viewModel.sendMessage()
            binding.etMessageInput.setText("")
        }
    }

    private fun initRecycler(): TiRecyclerCoroutines<ViewTyped> {
        recycler.adapter.registerAdapterDataObserver(scrollToBottomObserver)
        return recycler.apply(::handleClicks)
    }

    private fun handleClicks(recycler: TiRecyclerCoroutines<ViewTyped>) {
        recycler.longClickedItem<MessageUi>(MessageUi.VIEW_TYPE)
            .onEach(::pickReactionForMessage)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)
    }

    private fun pickReactionForMessage(messageUi: MessageUi) {
        parentFragmentManager.setFragmentResultListener(
            PickEmojiDialog.EMOJI_RESULT_KEY, viewLifecycleOwner
        ) { _, bundle ->
            onEmojiPicked(bundle, messageUi)
        }

        parentFragmentManager.commit {
            add(PickEmojiDialog(), null)
            addToBackStack(null)
        }
    }

    private fun onEmojiPicked(bundle: Bundle, messageUi: MessageUi) {
        val emojiName = requireNotNull(bundle.getString(PickEmojiDialog.EMOJI_RESULT_KEY))
        viewModel.sendOrRevokeReaction(messageUi, emojiName)
    }

    private fun observeState(recycler: TiRecyclerCoroutines<ViewTyped>) {
        listOf(
            viewModel.state
                .map { state -> state.isLoading }
                .onEach(::displayLoading),
            viewModel.state
                .map { state -> state.error }
                .onEach(::displayError),
            viewModel.state
                .map { state -> state.items }
                .onEach(recycler::setItems),
            viewModel.state
                .map { state -> state.isSendButtonVisible }
                .onEach(::setSendButtonVisibility),
        ).forEach { dataFlow ->
            dataFlow.collectWhenStarted(viewLifecycleOwner.lifecycle)
        }
    }

    private fun displayLoading(isLoading: Boolean) {
        binding.progressIndicator.isVisible = isLoading
    }

    private fun displayError(error: Throwable?) {
        binding.tvError.isVisible = error != null
        if (error != null) {
            binding.tvError.text = error.toString()
        }
    }

    private fun setSendButtonVisibility(visible: Boolean) {
        binding.ibAdd.isVisible = !visible
        binding.ibSend.isVisible = visible
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler.adapter.unregisterAdapterDataObserver(scrollToBottomObserver)
    }

    inner class ScrollToBottomOnItemsAdded : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            binding.rvChat.smoothScrollToPosition(positionStart + itemCount)
        }
    }

    companion object {
        private const val KEY_STREAM = "KEY_STREAM"
        private const val KEY_TOPIC = "KEY_TOPIC"

        fun newInstance(stream: Int, topic: String) = TopicFragment().apply {
            arguments = bundleOf(
                KEY_STREAM to stream,
                KEY_TOPIC to topic
            )
        }
    }
}
