package com.example.feature.main.api

import com.example.coursework.core.di.BaseDeps
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface MainDeps : BaseDeps {
    fun getStreamsScreen(): FragmentScreen
    fun getPeopleScreen(): FragmentScreen
    fun getProfileScreen(): FragmentScreen
}
