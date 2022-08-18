package com.luceno.firebasechat.feature_chat.data.datasources

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.luceno.firebasechat.feature_chat.domain.models.Message
import kotlinx.coroutines.tasks.await

class MessagesRemoteDataSourceImpl: MessagesRemoteDataSource {

    private val TAG = MessagesRemoteDataSourceImpl::class.qualifiedName

    override suspend fun send(message: Message): String {

        return try {
            Firebase.firestore.collection("messages")
                .add(
                    hashMapOf(
                        "image" to message.image,
                        "timestamp" to FieldValue.serverTimestamp(),
                        "message" to message.message,
                        "userUid" to message.userUid,
                        "displayName" to message.displayName
                    )
                ).await().id
        } catch ( e: Exception ) {
            Log.e(TAG, "Error send message.", e)
            throw RuntimeException()
        }

    }

    override fun onReceived(onMessages: (messages: List<Message>) -> Unit) {
        Firebase.firestore.collection("messages")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { value, e ->
                if(e != null){
                    Log.w("TAG", "Listen messages error.", e)
                    return@addSnapshotListener
                }

                val messages = value?.documents?.map { it.toObject(Message::class.java)!! }
                if(messages != null) {
                    onMessages(messages)
                }
            }
    }

    suspend fun fetchAll(): List<Message> {
        return try {
            Firebase.firestore.collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get().await()
                .documents
                .map { it.toObject(Message::class.java)!! }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetch messages.", e)
            throw RuntimeException()
        }
    }

}