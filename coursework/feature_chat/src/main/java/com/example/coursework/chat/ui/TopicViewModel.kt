package com.example.coursework.chat.ui

import com.example.core.ui.base.BaseViewModel
import com.example.coursework.chat.data.MessageRepositoryImpl
import com.example.coursework.chat.ui.elm.TopicEvent
import com.example.coursework.chat.ui.elm.TopicStoreFactory
import com.example.coursework.chat.ui.model.MessageUi
import com.example.coursework.shared.profile.data.UsersRepositoryImpl
import com.example.coursework.shared.profile.domain.usecase.GetCurrentUser

class TopicViewModel : BaseViewModel() {
    val store = TopicStoreFactory(
        MessageRepositoryImpl.instance,
        GetCurrentUser(UsersRepositoryImpl.instance)
    ).store

    fun toggleReaction(messageUi: MessageUi, emoteName: String) {
        store.accept(TopicEvent.Ui.ToggleReaction(messageUi, emoteName))
    }

    fun sendMessage() {
        store.accept(TopicEvent.Ui.ClickSendMessage)
    }

    fun setMessageInput(text: String) {
        store.accept(TopicEvent.Ui.UpdateInputText(text))
    }
}
