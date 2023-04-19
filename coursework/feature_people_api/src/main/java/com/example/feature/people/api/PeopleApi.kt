package com.example.feature.people.api

import com.example.coursework.core.di.BaseApi
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface PeopleApi : BaseApi {
    fun getPeopleScreen(): FragmentScreen
}
