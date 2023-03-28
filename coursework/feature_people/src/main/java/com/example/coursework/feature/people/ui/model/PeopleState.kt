package com.example.coursework.feature.people.ui.model

data class PeopleState(
    val isLoading: Boolean = true,
    val people: List<PeopleUi> = emptyList(),
    val notFound: Boolean = false,
    val error: Throwable? = null,
)
