package com.example.coursework.feature.people.impl.ui.elm

import com.example.coursework.feature.people.impl.ui.model.PeopleUi

data class PeopleState(
    val isLoading: Boolean = true,
    val people: List<PeopleUi> = emptyList(),
    val notFound: Boolean = false,
    val error: Throwable? = null,
)
