package com.luceno.firebasechat.feature_auth.presentation.signin

import androidx.annotation.StringRes

data class SignInState (
    val email: String = "",
    val password: String = "",
    val isPasswordVisibility: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
)