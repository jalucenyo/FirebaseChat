package com.luceno.firebasechat.shared.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.luceno.firebasechat.R

@Composable
fun GoogleSocialButton(
    onClick : () -> Unit,
    modifier: Modifier = Modifier
){
    SocialButton(
        text = stringResource(id = R.string.sign_in_with_google),
        onClick = onClick,
        icon = painterResource(id = R.drawable.ic_logo_google),
        color = Color.Black)}