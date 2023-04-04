package com.example.coursework.shared.profile.data

import com.example.coursework.shared.profile.data.model.PresenceDto
import com.example.coursework.shared.profile.data.model.UserDto
import com.example.coursework.shared.profile.domain.User
import com.example.coursework.shared.profile.domain.UserPresence
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
