package com.example.coursework.feature.people.impl.ui.elm

import com.example.coursework.feature.people.impl.di.PeopleScope
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

@PeopleScope
class PeopleReducer @Inject constructor() : DslReducer<PeopleEvent, PeopleState, PeopleEffect, PeopleCommand>() {
    override fun Result.reduce(event: PeopleEvent) =
        when (event) {
            is PeopleEvent.Init -> init()
            is PeopleEvent.Ui.UpdateSearchQuery -> findPeople(event)
            is PeopleEvent.Internal.CaughtError -> showError(event)
            is PeopleEvent.Internal.PeopleLoaded -> showPeople(event)
        }

    private fun Result.init() {
        commands {
            +PeopleCommand.LoadPeople
        }
    }

    private fun Result.findPeople(
        event: PeopleEvent.Ui.UpdateSearchQuery,
    ) {
        commands {
            +PeopleCommand.FindPeople(event.value)
        }
    }

    private fun Result.showError(
        event: PeopleEvent.Internal.CaughtError,
    ) {
        state {
            copy(
                isLoading = false,
                error = event.error
            )
        }
    }

    private fun Result.showPeople(
        event: PeopleEvent.Internal.PeopleLoaded,
    ) {
        state {
            copy(
                isLoading = false,
                people = event.people,
                notFound = event.people.isEmpty(),
                error = null
            )
        }
    }
}
