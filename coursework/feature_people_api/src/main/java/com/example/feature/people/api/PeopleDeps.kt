package com.example.feature.people.api

import com.example.coursework.core.di.BaseDeps
import com.example.shared.profile.api.domain.usecase.GetOtherUsers

interface PeopleDeps : BaseDeps {
    val getOtherUsers: GetOtherUsers
}
