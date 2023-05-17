package com.example.feature.create_stream.impl.domain

import javax.inject.Inject

class GetIsStreamNameValid @Inject constructor() {
    fun execute(occupiedStreamNames: List<String>?, streamName: String): IsStreamNameValid {
        return when {
            occupiedStreamNames == null -> IsStreamNameValid.WAIT_A_BIT
            streamName.isBlank() -> IsStreamNameValid.NAME_BLANK
            streamName in occupiedStreamNames -> IsStreamNameValid.NAME_OCCUPIED
            else -> IsStreamNameValid.VALID
        }
    }
}
