package com.example.core.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen

interface Screens {
    fun main(): FragmentScreen
    fun chat(): FragmentScreen
    fun channels(): FragmentScreen
    fun people(): FragmentScreen
    fun profile(): FragmentScreen
}
