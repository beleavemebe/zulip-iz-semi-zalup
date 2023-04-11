package com.example.coursework.feature.people.ui.elm

import com.example.coursework.feature.people.ui.model.PeopleUi

data class PeopleState(
    val isLoading: Boolean = true,
    val people: List<PeopleUi> = emptyList(),
    val notFound: Boolean = false,
    val error: Throwable? = null,
)
