package com.example.feature.create_stream.impl.ui.elm

sealed interface CreateStreamCommand {
    object LoadOccupiedStreamNames : CreateStreamCommand
    data class CreateStream(
        val occupiedStreamNames: List<String>?,
        val streamName: String,
    ) : CreateStreamCommand
}
