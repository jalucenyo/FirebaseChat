package com.luceno.firebasechat.shared.composable

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luceno.firebasechat.R

@Composable
fun PasswordField(value: String, onValueChange: (String) -> Unit, modifier: Modifier) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        singleLine = true,
        placeholder = { Text(text = stringResource(id = R.string.login_email)) }
    )
}