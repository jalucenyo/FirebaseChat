package com.luceno.firebasechat.feature_chat.data.repositories

import com.luceno.firebasechat.feature_chat.data.datasources.MessagesRemoteDataSource
import com.luceno.firebasechat.feature_chat.domain.models.Message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class MessagesRepositoryImplTest {

    @Mock
    private lateinit var messagesRemoteDataSource: MessagesRemoteDataSource

    @Test
    @ExperimentalCoroutinesApi
    fun `should return id when send message`(){
        runTest {

            // Given
            `when`(messagesRemoteDataSource.send(any())).thenReturn("testId")
            val message = Message(
                message = "Test message",
                image = "Test uri",
                displayName = "Test display name",
                userUid = "Test user Id"
            )

            // When
            val repository = MessagesRepositoryImpl(messagesRemoteDataSource)
            val result = repository.send(message)

            // Then

            verify(messagesRemoteDataSource).send(message)
            assert(result.isNotBlank())

        }
    }



}