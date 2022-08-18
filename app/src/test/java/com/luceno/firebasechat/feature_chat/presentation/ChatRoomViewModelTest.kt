package com.luceno.firebasechat.feature_chat.presentation

import com.luceno.firebasechat.feature_auth.services.AccountService
import com.luceno.firebasechat.feature_chat.domain.models.Message
import com.luceno.firebasechat.feature_chat.domain.repositories.MessagesRepository
import com.luceno.firebasechat.feature_chat.domain.repositories.StorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ChatRoomViewModelTest {


    @Mock
    private lateinit var accountService: AccountService
    @Mock
    private lateinit var messagesRepository: MessagesRepository
    @Mock
    private lateinit var storageRepository: StorageRepository

    val userIdTest = "userIdTest"
    val userDisplayNameTest = "userDisplayNameTest"

    @Before
    fun setup(){
        Dispatchers.setMain(StandardTestDispatcher())
        Mockito.`when`(accountService.getUserUid()).thenReturn(userIdTest)
        Mockito.`when`(accountService.getDisplayName()).thenReturn(userDisplayNameTest)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `given a message when send then invoke repository sendMessage and update state`() = runTest {

        // Given
        val subject = ChatRoomViewModel(accountService, messagesRepository, storageRepository)

        val message = Message(
            image = "",
            message = "TestMessageContent",
            userUid = userIdTest,
            displayName = userDisplayNameTest
        )

        subject.state.value = subject.state.value.copy(
            message = message.message!!
        )

        Mockito.`when`(messagesRepository.send(any())).thenReturn("IdTestMessage")

        // When
        subject.sendMessage()

        // Then
        launch {
            verify(messagesRepository).send(message)
            assertEquals("", subject.state.value.message)
            assertEquals(null, subject.state.value.uploadImageUri)
            assertEquals(false, subject.state.value.isShowUploadImage)
            assertEquals(false, subject.state.value.isSending)
        }
    }


    @Test
    fun `given a message with inage when send then `() = runTest {

        // Given
        val subject = ChatRoomViewModel(accountService, messagesRepository, storageRepository)

        val message = Message(
            image = "urlDownloadImageTest",
            message = "TestMessageContent",
            userUid = userIdTest,
            displayName = userDisplayNameTest
        )

        subject.state.value = subject.state.value.copy(
            message = message.message!!,
            uploadImageUri = "uriImage"
        )

        Mockito.`when`(messagesRepository.send(any())).thenReturn("IdTestMessage")
        Mockito.`when`(storageRepository.uploadFile(any())).thenReturn("urlDownloadImageTest")

        // When
        subject.sendMessage()

        // Then
        launch {
            verify(messagesRepository).send(message)
            assertEquals("", subject.state.value.message)
            assertEquals(null, subject.state.value.uploadImageUri)
            assertEquals(false, subject.state.value.isShowUploadImage)
            assertEquals(false, subject.state.value.isSending)
        }
    }

}