package com.example.coursework.topic.impl.ui

import androidx.lifecycle.ViewModel
import com.example.coursework.topic.impl.TopicFacade
import com.example.coursework.topic.impl.ui.elm.TopicEvent
import com.example.coursework.topic.impl.ui.elm.TopicStoreFactory

class TopicViewModel(
    storeFactory: TopicStoreFactory
) : ViewModel() {
    val store = storeFactory.store

    fun toggleReaction(messageId: Int, emoteName: String) {
        store.accept(TopicEvent.Ui.ToggleReaction(messageId, emoteName))
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

    fun deleteMessage(messageId: Int) {
        store.accept(TopicEvent.Ui.DeleteMessage(messageId))
    }

    fun editMessage(messageId: Int, oldContent: String, updatedContent: String) {
        store.accept(TopicEvent.Ui.EditMessage(messageId, oldContent, updatedContent))
    }

    fun copyMessage(message: String) {
        store.accept(TopicEvent.Ui.CopyMessage(message))
    }

    override fun onCleared() {
        super.onCleared()
        TopicFacade.clear()
    }
}
