package com.luceno.firebasechat.feature_auth.presentation.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luceno.firebasechat.R
import com.luceno.firebasechat.feature_auth.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor (
    val accountService: AccountService
): ViewModel() {

    var state = mutableStateOf(SignUpState())
        private set

    fun onNameValueChange(newValue: String) {
        state.value = state.value.copy(name = newValue)
    }

    fun onEmailValueChange(newValue: String) {
        state.value = state.value.copy(email = newValue)
    }

    fun onPasswordValueChange(newValue: String){
        state.value = state.value.copy(password = newValue)
    }

    fun onRepeatPasswordValueChange(newValue: String){
        state.value = state.value.copy(repeatPassword = newValue)
    }

    fun onPasswordVisibility(){
        state.value = state.value.copy(isVisibilityPassword = !state.value.isVisibilityPassword)
    }

    fun onRepeatPasswordVisibility(){
        state.value = state.value
            .copy(isVisibilityRepeatPassword = !state.value.isVisibilityRepeatPassword)
    }

    fun onSignUp(onSuccess: () -> Unit) = viewModelScope.launch {
        try {
            state.value = state.value.copy(isLoading = true)
            accountService.signUpWithEmail(state.value.email, state.value.password)
            onSuccess()
        } catch (exception: Exception){
            state.value = state.value.copy(errorMessage = R.string.error_sign_up)
        }
        state.value = state.value.copy(isLoading = false)
    }

}