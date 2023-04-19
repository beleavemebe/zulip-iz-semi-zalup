package com.example.coursework.feature.people.impl

import com.example.coursework.core.di.FeatureFacade
import com.example.coursework.feature.people.impl.di.DaggerPeopleComponent
import com.example.coursework.feature.people.impl.di.PeopleComponent
import com.example.coursework.feature.people.impl.ui.PeopleFragment
import com.example.feature.people.api.PeopleApi
import com.example.feature.people.api.PeopleDeps
import com.github.terrakok.cicerone.androidx.FragmentScreen

object PeopleFacade : FeatureFacade<PeopleDeps, PeopleApi, PeopleComponent>() {
    override fun createComponent(deps: PeopleDeps): PeopleComponent {
        return DaggerPeopleComponent.factory().create(deps)
    }

    override fun createApi(component: PeopleComponent, deps: PeopleDeps): PeopleApi {
        return object : PeopleApi {
            override fun getPeopleScreen() = FragmentScreen {
                PeopleFragment()
            }
        }
    }
}
