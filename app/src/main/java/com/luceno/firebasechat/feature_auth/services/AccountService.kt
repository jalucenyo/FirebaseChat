package com.luceno.firebasechat.feature_auth.services

import android.content.Context
import android.content.Intent

interface AccountService {

    fun isSignIn(): Boolean

    fun authenticateWithEmail(email: String, password: String, onResult: (Throwable?) -> Unit)

    fun authenticateWithGoogle(context: Context): Intent

    fun getDisplayName(): String

    fun getUserUid(): String
}