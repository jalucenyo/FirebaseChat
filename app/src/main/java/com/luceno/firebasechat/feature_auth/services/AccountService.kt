package com.luceno.firebasechat.feature_auth.services

import android.content.Context
import android.content.Intent

interface AccountService {

    fun isSignIn(): Boolean

    fun signInWithEmail(email: String, password: String, onResult: (Throwable?) -> Unit)

    fun signInWithGoogle(context: Context): Intent

    fun getDisplayName(): String

    fun getUserUid(): String

    suspend fun signUpWithEmail(email: String, password: String)
}