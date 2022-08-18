package com.luceno.firebasechat.feature_auth.presentation.signin

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.luceno.firebasechat.feature_auth.services.AccountService
import kotlinx.coroutines.launch


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService
): ViewModel() {

    var state = mutableStateOf(SignInState())
        private set

    fun checkIsSignIn(onSuccess: (String) -> Unit) = viewModelScope.launch {
        if(accountService.isSignIn()) onSuccess(Firebase.auth.currentUser!!.uid)
    }

    fun onEmailChange(newValue: String) {
        state.value = state.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String){
        state.value = state.value.copy(password = newValue)
    }

    fun onSignInWithEmail(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            accountService.authenticateWithEmail(
                email = state.value.email, password = state.value.password,
                onResult = { error ->
                    Log.e("SingIn", """Error: ${error?.message}""")
                    onSuccess(Firebase.auth.currentUser!!.uid)
                }
            )
        }
    }

    fun onSignInWithGoogle(context: Context, onCallSignIn: (intent: Intent) -> Unit) {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("471939614688-hm3vuqjuemc9bn8usa38t2kd3ofr625n.apps.googleusercontent.com")
//            .requestEmail()
//            .build()
//
//        val signInIntent = GoogleSignIn.getClient(context, gso).signInIntent
        onCallSignIn(accountService.authenticateWithGoogle(context))
    }

    fun onResponseSignInWithGoogle(result: ActivityResult, onSuccess: (String) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            Firebase.auth.signInWithCredential(credential).addOnCompleteListener {
                onSuccess(Firebase.auth.currentUser!!.uid)
            }
        }catch (e: ApiException){
            Log.e("TAG", "Sign In Google Failed")
        }
    }

}