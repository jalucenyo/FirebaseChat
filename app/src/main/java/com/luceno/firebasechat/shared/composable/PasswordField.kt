package com.luceno.firebasechat.shared.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.luceno.firebasechat.R

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = stringResource(id = R.string.password),
    visibility: Boolean = false,
    imeAction: ImeAction = ImeAction.None,
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit,
    onVisibilityClick: () -> Unit,
    enabled: Boolean = true,
)
{
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .composed { modifier },
        enabled = enabled,
        value = value,
        onValueChange = { onValueChange(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        trailingIcon = {
            IconButton(
                onClick = onVisibilityClick
            )
            {
               Icon(imageVector = if(visibility) Icons.Default.VisibilityOff
                    else Icons.Default.Visibility,
                   contentDescription = "show password")
            }
        },
        visualTransformation = if(visibility) VisualTransformation.None
            else PasswordVisualTransformation(),
        placeholder = { Text(text = placeholder) }
    )
}