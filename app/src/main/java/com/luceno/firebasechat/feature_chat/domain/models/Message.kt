package com.luceno.firebasechat.feature_chat.domain.models


data class Message(
    val message: String? = null,
    val userUid: String = "",
    val displayName: String = "",
    val image: String? = null,
)
