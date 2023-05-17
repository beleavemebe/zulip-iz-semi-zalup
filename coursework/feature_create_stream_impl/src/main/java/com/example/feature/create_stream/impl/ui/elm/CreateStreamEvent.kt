package com.example.feature.create_stream.impl.ui.elm

import com.example.feature.create_stream.impl.domain.IsStreamNameValid

sealed interface CreateStreamEvent {
    sealed interface Ui : CreateStreamEvent {
        object Init : Ui
        object ClickGoBack : CreateStreamEvent
        data class UpdateStreamTitle(val value: String) : CreateStreamEvent
        object ClickCreateStream : CreateStreamEvent
    }

    sealed interface Internal : CreateStreamEvent {
        data class OccupiedStreamNamesLoaded(val values: List<String>) : Internal
        data class StreamCreateResultObtained(val isStreamNameValid: IsStreamNameValid) : Internal
    }
}
