package com.example.coursework.feature.people.ui.model

data class PeopleState(
    val people: List<PeopleUi> = emptyList(),
    val notFound: Boolean = false,
)
