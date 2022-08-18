package com.luceno.firebasechat.feature_chat.domain.repositories

interface StorageRepository {

    suspend fun uploadFile(uri: String): String

}