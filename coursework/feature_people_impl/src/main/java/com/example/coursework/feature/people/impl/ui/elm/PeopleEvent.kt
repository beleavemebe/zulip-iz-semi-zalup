package com.example.coursework.feature.people.impl.ui.elm

import com.example.coursework.feature.people.impl.ui.model.PeopleUi

sealed interface PeopleEvent {
    object Init : PeopleEvent

    sealed interface Ui : PeopleEvent {
        data class UpdateSearchQuery(val value: String) : Ui
    }

    sealed interface Internal : PeopleEvent {
        data class PeopleLoaded(val people: List<PeopleUi>) : Internal
        data class CaughtError(val error: Throwable) : Internal
    }
}
