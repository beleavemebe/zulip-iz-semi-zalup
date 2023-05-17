package com.example.coursework.topic.impl.ui.actions.edit_message

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.example.core.ui.argument
import com.example.coursework.topic.impl.TopicFacade
import com.example.coursework.topic.impl.ui.actions.MessageAction
import com.example.feature.topic.impl.R
import com.example.feature.topic.impl.databinding.DialogEditMessageBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditMessageDialog : BottomSheetDialogFragment(R.layout.dialog_edit_message) {
    private val messageId: Int by argument(KEY_MESSAGE_ID)
    private val oldContent: String by argument(KEY_MESSAGE_CONTENT)

    private lateinit var binding: DialogEditMessageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogEditMessageBinding.bind(view)
        initUi()
    }

    private fun initUi() {
        binding.tiMessageContent.editText!!.setText(oldContent)
        binding.btnDone.setOnClickListener {
            onDoneCLicked()
        }
    }

    private fun onDoneCLicked() {
        val updatedContent = binding.tiMessageContent.editText!!.text
        if (updatedContent.isBlank()) {
            binding.tiMessageContent.error = getString(R.string.message_blank)
        } else {
            exitFragment(updatedContent)
        }
    }

    private fun exitFragment(updatedContent: CharSequence) {
        TopicFacade.actionsFlow.tryEmit(
            MessageAction.EditMessage(
                messageId,
                oldContent,
                updatedContent.toString()
            )
        )
        dismiss()
    }

    companion object {
        private const val KEY_MESSAGE_ID = "KEY_MESSAGE_ID"
        private const val KEY_MESSAGE_CONTENT = "KEY_MESSAGE_CONTENT"

        fun newInstance(messageId: Int, messageContent: String) = EditMessageDialog().apply {
            arguments = bundleOf(
                KEY_MESSAGE_ID to messageId,
                KEY_MESSAGE_CONTENT to messageContent
            )
        }
    }
}
