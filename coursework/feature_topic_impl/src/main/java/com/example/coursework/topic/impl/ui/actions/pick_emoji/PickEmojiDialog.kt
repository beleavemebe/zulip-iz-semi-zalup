package com.example.coursework.topic.impl.ui.actions.pick_emoji

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import com.example.core.ui.argument
import com.example.coursework.topic.impl.TopicFacade
import com.example.coursework.topic.impl.ui.actions.MessageAction
import com.example.coursework.topic.impl.util.emojis
import com.example.feature.topic.impl.R
import com.example.feature.topic.impl.databinding.DialogPickEmojiBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PickEmojiDialog : BottomSheetDialogFragment(R.layout.dialog_pick_emoji) {
    private val messageId: Int by argument(KEY_MESSAGE_ID)

    private lateinit var binding: DialogPickEmojiBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogPickEmojiBinding.bind(view)
        populateFlexbox()
    }

    private fun populateFlexbox() {
        val inflater = LayoutInflater.from(requireActivity())
        val slice = emojis.slice(0..100)
        slice.forEach { emoji ->
            val textView = inflater.inflate(R.layout.item_emoji, binding.fbEmojis, false) as TextView
            textView.text = emoji.getCodeString()
            textView.setOnClickListener {
                exitFragment(emoji.name)
            }
            binding.fbEmojis.addView(textView)
        }
    }

    private fun exitFragment(emoji: String) {
        TopicFacade.actionsFlow.tryEmit(
            MessageAction.PickReaction(messageId, emoji)
        )
        dismiss()
    }

    companion object {
        private const val KEY_MESSAGE_ID = "KEY_MESSAGE_ID"

        fun newInstance(messageId: Int) = PickEmojiDialog().apply {
            arguments = bundleOf(KEY_MESSAGE_ID to messageId)
        }
    }
}
