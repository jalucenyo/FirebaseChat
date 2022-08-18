package com.luceno.firebasechat.feature_chat.domain.repositories

import com.luceno.firebasechat.feature_chat.domain.models.Message

interface MessagesRepository {

    suspend fun send(message: Message): String

    fun onReceived(onMessages: (messages: List<Message>) -> Unit)

}