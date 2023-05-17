package com.example.coursework.feature.create_topic.impl.ui

import androidx.lifecycle.ViewModel
import com.example.coursework.feature.create_topic.impl.CreateTopicFacade
import com.example.coursework.feature.create_topic.impl.ui.elm.CreateTopicEvent
import com.example.coursework.feature.create_topic.impl.ui.elm.CreateTopicStoreFactory

class CreateTopicViewModel(
    storeFactory: CreateTopicStoreFactory
) : ViewModel() {
    val store = storeFactory.store

    fun clickGoBack() {
        store.accept(CreateTopicEvent.Ui.ClickGoBack)
    }

    fun clickCreateTopic() {
        store.accept(CreateTopicEvent.Ui.ClickCreateTopic)
    }

    fun updateTopicTitle(text: String) {
        store.accept(CreateTopicEvent.Ui.UpdateTopicTitle(text))
    }

    fun updateFirstMessage(text: String) {
        store.accept(CreateTopicEvent.Ui.UpdateFirstMessage(text))
    }

    override fun onCleared() {
        super.onCleared()
        CreateTopicFacade.clear()
    }
}
