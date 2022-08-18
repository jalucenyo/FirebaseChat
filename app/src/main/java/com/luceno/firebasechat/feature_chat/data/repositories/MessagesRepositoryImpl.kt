package com.luceno.firebasechat.feature_chat.data.repositories

import com.luceno.firebasechat.feature_chat.data.datasources.MessagesRemoteDataSource
import com.luceno.firebasechat.feature_chat.domain.models.Message
import com.luceno.firebasechat.feature_chat.domain.repositories.MessagesRepository
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor (
    private val remoteDataSource: MessagesRemoteDataSource
) : MessagesRepository {

    override suspend fun send(message: Message): String = remoteDataSource.send(message)

    override fun onReceived(onMessages: (messages: List<Message>) -> Unit)
        = remoteDataSource.onReceived(onMessages)

}