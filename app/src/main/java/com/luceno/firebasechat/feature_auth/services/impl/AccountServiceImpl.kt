package com.luceno.firebasechat.feature_auth.services.impl

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.luceno.firebasechat.BuildConfig
import com.luceno.firebasechat.feature_auth.services.AccountService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor() : AccountService {

    override fun isSignIn(): Boolean = Firebase.auth.currentUser != null

    override fun getDisplayName(): String
        = Firebase.auth.currentUser!!.email!!.split("@")[0]

    override fun getUserUid(): String = Firebase.auth.currentUser?.uid ?: ""

    override fun signInWithEmail(email: String, password: String,
                                 onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun signInWithGoogle(context: Context): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_REQUEST_ID_TOKEN)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso).signInIntent
    }

    override suspend fun signUpWithEmail(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .await()
    }
}