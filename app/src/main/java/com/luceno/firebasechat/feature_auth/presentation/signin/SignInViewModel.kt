package com.luceno.firebasechat.feature_auth.presentation.signin

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.luceno.firebasechat.R
import com.luceno.firebasechat.feature_auth.services.AccountService
import kotlinx.coroutines.launch


@HiltViewModel
class SignInViewModel @Inject constructor (
    private val accountService: AccountService
): ViewModel() {

    var state = mutableStateOf(SignInState())
        private set

    fun checkIsSignIn(onSuccess: () -> Unit) = viewModelScope.launch {
        if(accountService.isSignIn()) onSuccess()
    }

    fun onEmailChange(newValue: String) {
        state.value = state.value.copy( email = newValue, errorMessage = null)
    }

    fun onPasswordChange(newValue: String){
        state.value = state.value.copy(password = newValue, errorMessage = null)
    }

    fun onPasswordVisibility(){
        state.value = state.value.copy(isPasswordVisibility = !state.value.isPasswordVisibility)
    }

    fun onSignInWithEmail(onSuccess: () -> Unit) {

        // TODO : Validate values!!!
        if(state.value.email.isNotBlank() or state.value.password.isNotBlank()){
            state.value = state.value.copy(isLoading = true)
            accountService.signInWithEmail(
                email = state.value.email,
                password = state.value.password
            ) { error ->
                state.value = state.value.copy(isLoading = false)
                if (error != null) {
                    state.value = state.value.copy(errorMessage = R.string.error_login)
                } else {
                    state.value = state.value.copy(errorMessage = null)
                    onSuccess()
                }
            }
        } else {
            state.value = state.value.copy(errorMessage = R.string.error_login)
        }

    }

    fun onSignInWithGoogle (
        context: Context,
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        val intent = accountService.signInWithGoogle(context)
        launcher.launch(intent)
    }

    fun onResponseSignInWithGoogle(result: ActivityResult, onSuccess: () -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            Firebase.auth.signInWithCredential(credential).addOnCompleteListener {
                onSuccess()
            }
        }catch (exception: ApiException){
            Log.e("TAG", "Sign In Google Failed")
            state.value = state.value.copy(errorMessage = R.string.error_sign_in_google)
        }
    }

}