package com.example.feature.create_stream.impl.domain

import androidx.annotation.StringRes
import com.example.feature.create_stream.impl.R

enum class IsStreamNameValid(
    @StringRes val stringRes: Int
) {
    WAIT_A_BIT(R.string.wait_a_bit),
    NAME_BLANK(R.string.name_blank),
    NAME_OCCUPIED(R.string.name_occupied),
    VALID(R.string.valid);
}
