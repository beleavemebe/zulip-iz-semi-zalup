package com.example.coursework.feature.people.impl.ui.elm

sealed interface PeopleCommand {
    object LoadPeople : PeopleCommand
    data class FindPeople(val query: String) : PeopleCommand
}
