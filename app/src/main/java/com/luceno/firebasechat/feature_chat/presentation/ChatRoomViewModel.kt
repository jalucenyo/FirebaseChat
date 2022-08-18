package com.luceno.firebasechat.feature_chat.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.luceno.firebasechat.feature_auth.services.AccountService
import com.luceno.firebasechat.feature_chat.domain.models.Message
import com.luceno.firebasechat.feature_chat.domain.repositories.MessagesRepository
import com.luceno.firebasechat.feature_chat.domain.repositories.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val accountService: AccountService,
    private val messagesRepository: MessagesRepository,
    private val storageRepository: StorageRepository,
): ViewModel() {

    var state = mutableStateOf(ChatRoomState())
        private set
    var _messages = mutableStateListOf<Message>()
        private set
    val messages: List<Message> = _messages

    init {
        receivedMessages()
    }

    fun closeShowImageMessage(){
        state.value = state.value.copy(
            isShowImageMessage = false,
            imageMessageUri = null
        )
    }

    fun closeUploadImage(){
        state.value = state.value.copy(
            isShowUploadImage = false,
            uploadImageUri = null
        )
    }

    fun showImageOfMessage(imageUri: String?){
        state.value = state.value.copy(
            isShowImageMessage = true,
            imageMessageUri = imageUri
        )
    }

    fun selectedUploadImage(imageUri: String?) {
        state.value = state.value.copy(
            isShowUploadImage = true,
            uploadImageUri = imageUri,
        )
    }

    fun isSendEnabled(): Boolean = !state.value.isSending and
            (state.value.message.isNotBlank() or (state.value.uploadImageUri != null))

    fun isCurrentUser(message: Message): Boolean = message.userUid == accountService.getUserUid()

    fun onInputMessageChange(newValue: String) {
        state.value = state.value.copy(message = newValue)
    }

    fun sendMessage() = viewModelScope.launch {
        state.value = state.value.copy(isSending = true)

        var downloadUri = ""
        if(state.value.uploadImageUri != null){
            downloadUri = storageRepository
                .uploadFile(state.value.uploadImageUri!!)
        }

        messagesRepository.send(
            Message(
                image = downloadUri,
                message = state.value.message,
                userUid = accountService.getUserUid(),
                displayName = accountService.getDisplayName()
            )
        )

        state.value = state.value.copy(
            message = "",
            uploadImageUri = null,
            isShowUploadImage = false,
            isSending = false
        )

    }

    private fun receivedMessages() {
        messagesRepository.onReceived {
            _messages.clear()
            _messages.addAll(it)
        }
    }

    fun onSingOut(onSignOut: () -> Unit) {
        Firebase.auth.signOut()
        onSignOut()
    }

}