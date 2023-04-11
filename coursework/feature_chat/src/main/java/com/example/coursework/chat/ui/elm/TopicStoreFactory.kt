package com.example.coursework.chat.ui.elm

import com.example.coursework.chat.domain.MessageRepository
import com.example.coursework.shared.profile.domain.usecase.GetCurrentUser
import vivid.money.elmslie.coroutines.ElmStoreCompat

class TopicStoreFactory(
    messageRepository: MessageRepository,
    getCurrentUser: GetCurrentUser,
) {
    val store by lazy {
        ElmStoreCompat(
            initialState = TopicState(),
            reducer = TopicReducer(),
            actor = TopicActor(messageRepository, getCurrentUser)
        )
    }
}
