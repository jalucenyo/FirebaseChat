package com.luceno.firebasechat.feature_chat.presentation

data class ChatRoomState(
    val message: String = "",
    val isSending: Boolean = false,
    val isShowUploadImage: Boolean = false,
    val uploadImageUri: String? = null,
    val isShowImageMessage: Boolean = false,
    val imageMessageUri: String? = null,
)
