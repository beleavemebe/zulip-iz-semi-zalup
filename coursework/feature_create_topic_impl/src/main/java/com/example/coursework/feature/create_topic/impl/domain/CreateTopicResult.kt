package com.example.coursework.feature.create_topic.impl.domain

import androidx.annotation.StringRes
import com.example.coursework.feature.create_topic.api.R

enum class CreateTopicResult(
    @StringRes val stringRes: Int
) {
    WAIT_A_BIT(R.string.wait_a_bit),
    NAME_BLANK(R.string.name_blank),
    NAME_OCCUPIED(R.string.name_occupied),
    MESSAGE_BLANK(R.string.message_blank),
    VALID(R.string.valid);
}
