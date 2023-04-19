package com.example.coursework.main.di

import com.example.coursework.core.di.DaggerComponent

interface MainComponent : DaggerComponent {
    object Stub : MainComponent
}
