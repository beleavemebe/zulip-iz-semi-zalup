package com.example.coursework.topic.impl.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.ui.argument
import com.example.core.ui.assistedViewModel
import com.example.coursework.core.utils.collectWhenStarted
import com.example.coursework.core.utils.copyStringToClipboard
import com.example.coursework.topic.impl.TopicFacade
import com.example.coursework.topic.impl.ui.actions.ActionsDialog
import com.example.coursework.topic.impl.ui.actions.MessageAction
import com.example.coursework.topic.impl.ui.actions.pick_emoji.PickEmojiDialog
import com.example.coursework.topic.impl.ui.elm.TopicEffect
import com.example.coursework.topic.impl.ui.elm.TopicEvent
import com.example.coursework.topic.impl.ui.elm.TopicState
import com.example.coursework.topic.impl.ui.elm.TopicStoreFactory
import com.example.coursework.topic.impl.ui.model.ForeignMessageUi
import com.example.coursework.topic.impl.ui.model.MessageUi
import com.example.coursework.topic.impl.ui.model.OwnMessageUi
import com.example.coursework.topic.impl.ui.model.TopicItem
import com.example.coursework.topic.impl.ui.recycler.TopicViewHolderFactory
import com.example.feature.topic.impl.R
import com.example.feature.topic.impl.databinding.FragmentTopicBinding
import kotlinx.coroutines.flow.onEach
import ru.tinkoff.mobile.tech.ti_recycler.adapters.AsyncTiAdapter
import ru.tinkoff.mobile.tech.ti_recycler.adapters.BaseTiAdapter
import ru.tinkoff.mobile.tech.ti_recycler.base.diff.ViewTypedDiffCallback
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.TiRecyclerCoroutines
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.base.CoroutinesHolderFactory
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import javax.inject.Inject

class TopicFragment : ElmFragment<TopicEvent, TopicEffect, TopicState>(R.layout.fragment_topic) {
    private val streamId: Int by argument(KEY_STREAM_ID)
    private val stream: String by argument(KEY_STREAM)
    private val topic: String by argument(KEY_TOPIC)

    @Inject lateinit var storeFactory: TopicStoreFactory
    private val viewModel by assistedViewModel { TopicViewModel(storeFactory) }
    private val binding by viewBinding(FragmentTopicBinding::bind)

    private val adapter: BaseTiAdapter<TopicItem, CoroutinesHolderFactory> by lazy {
        AsyncTiAdapter(
            TopicViewHolderFactory(viewModel::toggleReaction, ::pickReactionForMessage),
            ViewTypedDiffCallback(TopicItem.payloadMappers)
        )
    }

    private lateinit var recycler: TiRecyclerCoroutines<TopicItem>

    override val storeHolder by lazy {
        LifecycleAwareStoreHolder(lifecycle, viewModel::store)
    }

    override val initEvent by lazy {
        TopicEvent.Ui.Init(streamId, topic)
    }

    override fun onAttach(context: Context) {
        TopicFacade.component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionListener()
        initToolbar()
        initEditText()
        initSendButton()
        initRecycler()
    }

    private fun initActionListener() {
        TopicFacade.actionsFlow
            .onEach(::handleAction)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)
    }

    private fun handleAction(action: MessageAction) {
        when (action) {
            is MessageAction.PickReaction -> viewModel.toggleReaction(action.messageId, action.emoteName)
            is MessageAction.DeleteMessage -> viewModel.deleteMessage(action.messageId)
            is MessageAction.EditMessage -> viewModel.editMessage(action.messageId, action.oldContent, action.updatedContent)
            is MessageAction.CopyMessage -> viewModel.copyMessage(action.message)
        }
    }

    private fun initToolbar() {
        binding.toolbar.title = stream
        binding.toolbar.setNavigationOnClickListener { viewModel.goBack() }
        binding.tvTopic.text = getString(R.string.placeholder_topic, topic)
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

    private fun initRecycler() {
        recycler = TiRecyclerCoroutines(binding.rvChat, adapter)
        listOf(
            recycler.longClickedItem<ForeignMessageUi>(ForeignMessageUi.VIEW_TYPE),
            recycler.longClickedItem<OwnMessageUi>(OwnMessageUi.VIEW_TYPE)
        ).forEach { messageClickFlow ->
            messageClickFlow
                .onEach(::openActionsDialog)
                .collectWhenStarted(viewLifecycleOwner.lifecycle)
        }

        binding.rvChat.addOnScrollListener(
            PagingTriggeringScrollListener(
                binding.rvChat.layoutManager as LinearLayoutManager,
                viewModel::loadPreviousPage,
                viewModel::loadNextPage
            )
        )
    }

    private fun openActionsDialog(messageUi: MessageUi) {
        parentFragmentManager.commit {
            add(ActionsDialog.newInstance(messageUi.id, messageUi.message), null)
        }
    }

    private fun pickReactionForMessage(messageUi: MessageUi) {
        parentFragmentManager.commit {
            add(PickEmojiDialog.newInstance(messageUi.id), null)
        }
    }

    override fun render(state: TopicState) {
        displayLoading(state.isLoading)
        displayError(state.error)
        recycler.setItems(state.items)
        setSendButtonVisibility(state.isSendButtonVisible)
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

    override fun handleEffect(effect: TopicEffect): Unit? {
        return when (effect) {
            is TopicEffect.CopyMessage -> copyMessage(effect)
        }
    }

    private fun copyMessage(effect: TopicEffect.CopyMessage) {
        val copied = requireContext().copyStringToClipboard(effect.message)
        Toast.makeText(
            requireContext(),
            getString(if (copied) R.string.copied else R.string.not_copied),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvChat.clearOnScrollListeners()
    }

    companion object {
        private const val KEY_STREAM_ID = "KEY_STREAM_ID"
        private const val KEY_STREAM = "KEY_STREAM"
        private const val KEY_TOPIC = "KEY_TOPIC"

        @VisibleForTesting
        fun createArguments(streamId: Int, stream: String, topic: String) = bundleOf(
            KEY_STREAM_ID to streamId,
            KEY_STREAM to stream,
            KEY_TOPIC to topic
        )

        fun newInstance(streamId: Int, stream: String, topic: String) = TopicFragment().apply {
            arguments = createArguments(streamId, stream, topic)
        }
    }
}
