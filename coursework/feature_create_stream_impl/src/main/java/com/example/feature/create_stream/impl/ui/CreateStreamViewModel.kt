package com.example.feature.create_stream.impl.ui

import com.example.core.ui.base.BaseViewModel
import com.example.feature.create_stream.impl.CreateStreamFacade
import com.example.feature.create_stream.impl.ui.elm.CreateStreamEvent
import com.example.feature.create_stream.impl.ui.elm.CreateStreamStoreFactory

class CreateStreamViewModel(
    storeFactory: CreateStreamStoreFactory
) : BaseViewModel() {
    val store = storeFactory.store

    fun clickGoBack() {
        store.accept(CreateStreamEvent.Ui.ClickGoBack)
    }

    fun clickCreateStream() {
        store.accept(CreateStreamEvent.Ui.ClickCreateStream)
    }

    fun updateStreamTitle(text: String) {
        store.accept(CreateStreamEvent.Ui.UpdateStreamTitle(text))
    }

    override fun onCleared() {
        super.onCleared()
        CreateStreamFacade.clear()
    }
}
