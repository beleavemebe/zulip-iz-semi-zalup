package com.example.coursework.chat.ui.model

data class MessageModel(
    val author: String,
    val authorImageUrl: String,
    val message: String,
    val reactions: List<ReactionModel>,
)
