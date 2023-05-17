package com.example.coursework.shared_messages.api

import com.example.coursework.core.di.BaseApi
import com.example.coursework.shared_messages.api.domain.MessageRepository

interface SharedMessagesApi : BaseApi {
    val messageRepository: MessageRepository
}
