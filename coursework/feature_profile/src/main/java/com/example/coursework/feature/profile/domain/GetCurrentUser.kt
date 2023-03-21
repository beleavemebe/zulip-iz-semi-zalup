package com.example.coursework.feature.profile.domain

import com.example.coursework.shared.profile.domain.User
import java.util.UUID

class GetCurrentUser {
    fun execute(): User {
        return User(
            UUID.randomUUID().toString(),
            "Roman Shemanovskii",
            "romanshemanovskii@gmail.com",
            "busy (being successful)",
            User.OnlineStatus.ONLINE,
            "https://avatars.akamai.steamstatic.com/3c5cb445d28024791cde806777b1ca61c71ae7d9_full.jpg"
        )
    }
}
