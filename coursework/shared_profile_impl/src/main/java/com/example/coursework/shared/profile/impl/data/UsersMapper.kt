package com.example.coursework.shared.profile.impl.data

import com.example.coursework.shared.profile.impl.data.model.PresenceDto
import com.example.coursework.shared.profile.impl.data.model.UserDto
import com.example.shared.profile.api.domain.User
import com.example.shared.profile.api.domain.UserPresence
import java.time.Instant
import java.time.temporal.ChronoUnit

object UsersMapper {
    fun toUserPresence(dto: PresenceDto?): UserPresence {
        if (dto == null) return UserPresence.OFFLINE
        val minutesPassed = ChronoUnit.MINUTES.between(
            Instant.ofEpochSecond(dto.timestamp),
            Instant.now()
        )

        return if (minutesPassed > 5) {
            UserPresence.OFFLINE
        } else when (dto.status) {
            "active" -> UserPresence.ACTIVE
            "idle" -> UserPresence.IDLE
            else -> UserPresence.OFFLINE
        }
    }

    fun toUser(dto: UserDto, presenceDto: PresenceDto?): User {
        val presence = presenceDto.let(::toUserPresence)
        return User(
            id = dto.user_id,
            name = dto.full_name,
            email = dto.email,
            presence = presence,
            imageUrl = dto.avatar_url.orEmpty()
        )
    }
}
