package com.example.coursework.main.di

import com.example.coursework.core.di.FeatureFacade
import com.example.coursework.main.MainFragment
import com.example.feature.main.api.MainApi
import com.example.feature.main.api.MainDeps
import com.github.terrakok.cicerone.androidx.FragmentScreen

object MainFacade : FeatureFacade<MainDeps, MainApi, MainComponent>() {
    override fun createComponent(deps: MainDeps) = MainComponent.Stub

    override fun createApi(component: MainComponent, deps: MainDeps): MainApi {
        return object : MainApi {
            override fun getMainScreen() = FragmentScreen { MainFragment() }
        }
    }
}
