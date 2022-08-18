package com.luceno.firebasechat.feature_auth.presentation.signup

import androidx.annotation.StringRes

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isVisibilityPassword: Boolean = false,
    val isVisibilityRepeatPassword: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
)
