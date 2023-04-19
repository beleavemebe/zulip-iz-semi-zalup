package com.example.coursework.feature.people.impl.di

import com.example.coursework.core.di.DaggerComponent
import com.example.coursework.feature.people.impl.ui.PeopleFragment
import com.example.feature.people.api.PeopleDeps
import dagger.Component

@PeopleScope
@Component(
    dependencies = [PeopleDeps::class]
)
interface PeopleComponent : DaggerComponent {
    fun inject(peopleFragment: PeopleFragment)

    @Component.Factory
    interface Factory {
        fun create(deps: PeopleDeps): PeopleComponent
    }
}
