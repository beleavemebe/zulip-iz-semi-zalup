package com.example.feature.create_stream.impl.ui.elm

import com.example.feature.create_stream.impl.domain.IsStreamNameValid

data class CreateStreamState(
    val occupiedStreamNames: List<String>? = null,
    val streamName: String = "",
    val isStreamNameValid: IsStreamNameValid? = null
)
