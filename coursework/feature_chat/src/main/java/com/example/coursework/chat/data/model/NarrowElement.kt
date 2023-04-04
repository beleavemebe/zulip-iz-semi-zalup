package com.example.coursework.chat.data.model

@kotlinx.serialization.Serializable
data class Narrow(
    val operator: String,
    val operand: String,
)
