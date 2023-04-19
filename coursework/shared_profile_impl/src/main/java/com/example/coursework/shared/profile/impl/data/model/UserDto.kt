package com.example.coursework.shared.profile.impl.data.model

@kotlinx.serialization.Serializable
data class UserDto(
    val avatar_url: String?,
    val date_joined: String,
    val email: String,
    val full_name: String,
    val is_active: Boolean,
    val is_admin: Boolean,
    val is_billing_admin: Boolean,
    val is_bot: Boolean,
    val is_guest: Boolean,
    val is_owner: Boolean,
    val role: Int,
    val timezone: String,
    val user_id: Int
)
