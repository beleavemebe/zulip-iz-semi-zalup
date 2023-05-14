package com.example.coursework.topic.impl.ui

import com.example.core.ui.base.BaseViewModel
import com.example.coursework.topic.impl.TopicFacade
import com.example.coursework.topic.impl.ui.elm.TopicEvent
import com.example.coursework.topic.impl.ui.elm.TopicStoreFactory
import com.example.coursework.topic.impl.ui.model.MessageUi

class TopicViewModel(
    storeFactory: TopicStoreFactory
) : BaseViewModel() {
    val store = storeFactory.store

    fun toggleReaction(messageUi: MessageUi, emoteName: String) {
        store.accept(TopicEvent.Ui.ToggleReaction(messageUi, emoteName))
    }

    fun sendMessage() {
        store.accept(TopicEvent.Ui.ClickSendMessage)
    }

    fun setMessageInput(text: String) {
        store.accept(TopicEvent.Ui.UpdateInputText(text))
    }

    fun loadPreviousPage() {
        store.accept(TopicEvent.Ui.LoadPreviousPage)
    }

    fun loadNextPage() {
        store.accept(TopicEvent.Ui.LoadNextPage)
    }

    fun goBack() {
        store.accept(TopicEvent.Ui.ClickGoBack)
    }

    override fun onCleared() {
        super.onCleared()
        TopicFacade.clear()
    }
}
