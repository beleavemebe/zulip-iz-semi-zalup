package com.example.coursework.feature.channels.ui

import androidx.lifecycle.ViewModel
import com.example.coursework.core.di.ServiceLocator
import com.example.coursework.feature.channels.domain.Channel
import com.example.coursework.feature.channels.domain.GetAllChannels
import com.example.coursework.feature.channels.domain.GetSubscribedChannels
import com.example.coursework.feature.channels.ui.model.ChannelUi
import com.example.coursework.feature.channels.ui.model.ChannelsItem
import com.example.coursework.feature.channels.ui.model.ChannelsState
import com.example.coursework.feature.channels.ui.model.ChatUi
import com.example.coursework.shared.chat.Chat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChannelsViewModel : ViewModel() {
    private val getAllChannels = GetAllChannels()
    private val getSubscribedChannels = GetSubscribedChannels()
    private val router = ServiceLocator.globalRouter
    private val screens = ServiceLocator.screens
    private val _state = MutableStateFlow(
        ChannelsState(getSubscribedChannels.execute().map(::toChannelUi))
    )

    val state = _state.asStateFlow()

    fun clickChannel(channelUi: ChannelUi) {
        if (channelUi.isExpanded.not()) {
            showChatsForChannel(channelUi)
            channelUi.isExpanded = true
        } else {
            hideChatsOfChannel(channelUi)
            channelUi.isExpanded = false
        }
    }

    private fun showChatsForChannel(channelUi: ChannelUi) {
        val oldItems = state.value.items
        val iofChannel = oldItems.indexOf(channelUi)
        val newItems: List<ChannelsItem> =
            oldItems.slice(0 until iofChannel) +
                channelUi + channelUi.chatUis +
                oldItems.slice(iofChannel + 1 until oldItems.size)
        _state.value = ChannelsState(newItems)
    }

    private fun hideChatsOfChannel(channelUi: ChannelUi) {
        val oldItems = state.value.items
        val newItems = oldItems.filterNot { it in channelUi.chatUis }.toMutableList()
        _state.value = ChannelsState(newItems.toList())
    }

    fun clickChat(chatUi: ChatUi) {
        router.navigateTo(screens.chat())
    }

    private fun toChannelUi(
        channel: Channel
    ) = ChannelUi(channel.name, false, channel.chats.map(::toChatUi))

    private fun toChatUi(
        chat: Chat
    ) = ChatUi(chat.id, chat.name, chat.messageCount, chat.color)

    fun showSubscribedStreams() {
        _state.value = ChannelsState(getSubscribedChannels.execute().map(::toChannelUi))
    }

    fun showAllStreams() {
        _state.value = ChannelsState(getAllChannels.execute().map(::toChannelUi))
    }
}
