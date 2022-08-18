package com.luceno.firebasechat.feature_chat.data.repositories

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.luceno.firebasechat.feature_chat.domain.repositories.StorageRepository
import kotlinx.coroutines.tasks.await
import java.util.*

class StorageRepositoryImpl(

): StorageRepository {

    override suspend fun uploadFile(uri: String): String {
        val imageUid = UUID.randomUUID()
        return Firebase.storage.reference
            .child("images/${imageUid}")
            .putFile(Uri.parse(uri))
            .await().metadata?.reference?.downloadUrl?.await()
            .toString()!!
    }

}