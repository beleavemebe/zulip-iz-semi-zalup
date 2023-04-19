package com.example.coursework.topic.impl.data.model.dto

@kotlinx.serialization.Serializable
data class MessageDto(
    val avatar_url: String,
    val client: String,
    val content: String,
    val content_type: String,
    val display_recipient: String,
    val flags: List<String>,
    val id: Int,
    val is_me_message: Boolean,
    val reactions: List<ReactionDto>,
    val recipient_id: Int,
    val sender_email: String,
    val sender_full_name: String,
    val sender_id: Int,
    val sender_realm_str: String,
    val stream_id: Int,
    val subject: String,
    val timestamp: Int,
    val type: String
)
