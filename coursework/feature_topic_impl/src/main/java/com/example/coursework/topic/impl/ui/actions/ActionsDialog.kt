package com.example.coursework.topic.impl.ui.actions

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.ui.argument
import com.example.coursework.topic.impl.TopicFacade
import com.example.coursework.topic.impl.ui.actions.edit_message.EditMessageDialog
import com.example.coursework.topic.impl.ui.actions.pick_emoji.PickEmojiDialog
import com.example.feature.topic.impl.R
import com.example.feature.topic.impl.databinding.DialogActionsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ActionsDialog : BottomSheetDialogFragment(R.layout.dialog_actions) {
    private val messageId: Int by argument(KEY_MESSAGE_ID)
    private val messageContent: String by argument(KEY_MESSAGE_CONTENT)

    private val binding by viewBinding(DialogActionsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.actionPickReaction.root.setOnClickListener {
            showPickEmojiDialog()
        }
        binding.actionDeleteMessage.root.setOnClickListener {
            deleteMessage()
        }
        binding.actionEditMessage.root.setOnClickListener {
            editMessage()
        }
        binding.actionCopyMessage.root.setOnClickListener {
            copyMessage()
        }
    }

    private fun showPickEmojiDialog() = execAction { messageId ->
        parentFragmentManager.commit {
            add(PickEmojiDialog.newInstance(messageId), null)
        }
    }

    private fun deleteMessage() = execAction { messageId ->
        TopicFacade.actionsFlow.tryEmit(
            MessageAction.DeleteMessage(messageId)
        )
    }

    private fun editMessage() = execAction { messageId ->
        parentFragmentManager.commit {
            add(EditMessageDialog.newInstance(messageId, messageContent), null)
        }
    }

    private fun copyMessage() = execAction {
        TopicFacade.actionsFlow.tryEmit(
            MessageAction.CopyMessage(messageContent)
        )
    }

    private inline fun execAction(action: (messageId: Int) -> Unit) {
        action(messageId)
        dismiss()
    }

    companion object {
        private const val KEY_MESSAGE_ID = "KEY_MESSAGE_ID"
        private const val KEY_MESSAGE_CONTENT = "KEY_MESSAGE_CONTENT"

        fun newInstance(messageId: Int, messageContent: String) = ActionsDialog().apply {
            arguments = bundleOf(
                KEY_MESSAGE_ID to messageId,
                KEY_MESSAGE_CONTENT to messageContent
            )
        }
    }
}
