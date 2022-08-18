package com.luceno.firebasechat.feature_chat.data.datasources

import com.luceno.firebasechat.feature_chat.domain.models.Message

interface MessagesRemoteDataSource {

    suspend fun send(message: Message): String

    fun onReceived(onMessages: (messages: List<Message>) -> Unit)

}