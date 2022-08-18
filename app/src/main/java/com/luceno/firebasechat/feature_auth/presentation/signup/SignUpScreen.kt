package com.luceno.firebasechat.feature_auth.presentation.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luceno.firebasechat.R
import com.luceno.firebasechat.shared.composable.DividerOR
import com.luceno.firebasechat.shared.composable.GoogleSocialButton
import com.luceno.firebasechat.shared.composable.PasswordField
import com.luceno.firebasechat.shared.composable.TextBiColor
import com.luceno.firebasechat.shared.composable.TitleText
import com.luceno.firebasechat.ui.theme.FirebaseChatTheme


@Composable
fun SignUpScreen(
    state: SignUpState,
    onNameValueChange: (String) -> Unit,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onRepeatPasswordValueChange: (String) -> Unit,
    onVisibilityPassword: ()-> Unit,
    onVisibilityRepeatPassword: ()-> Unit,
    onNavigateToBack: () -> Unit,
    onSignUp: (onSuccess: () -> Unit) -> Unit,
    onSuccess: () -> Unit,
){

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(onClick = onNavigateToBack) {
                    Icon( 
                        imageVector = Icons.Default.ArrowBack, 
                        contentDescription = "Back Icon",
                        tint = MaterialTheme.colors.primary
                    )
                }
                Text(
                    text = stringResource(id = R.string.create_an_account),
                    style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.primary
                    )
                )
            }
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    enabled = !state.isLoading,
                    placeholder = { Text(text = stringResource(id = R.string.name_field)) },
                    value = state.name,
                    onValueChange = onNameValueChange)

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Email
                    ),
                    enabled = !state.isLoading,
                    placeholder = { Text(text = stringResource(id = R.string.email_field)) },
                    value = state.email,
                    onValueChange = onEmailValueChange)

                PasswordField(value = state.password,
                    visibility = state.isVisibilityPassword ,
                    enabled = !state.isLoading,
                    onValueChange = onPasswordValueChange,
                    onVisibilityClick = onVisibilityPassword)

                PasswordField(value = state.repeatPassword,
                    placeholder = stringResource(id = R.string.repeat_password),
                    enabled = !state.isLoading,
                    visibility = state.isVisibilityRepeatPassword,
                    onValueChange = onRepeatPasswordValueChange,
                    onVisibilityClick = onVisibilityRepeatPassword)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSignUp(onSuccess) },
                    enabled = !state.isLoading,
                    ) {
                    Text(text = stringResource(id = R.string.sign_up))
                }

                TextBiColor(
                    textStart = stringResource(id = R.string.already_have_an_account),
                    textEnd = stringResource(id = R.string.sign_in),
                    onClick = { onNavigateToBack() })
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DividerOR()
                TitleText(text = stringResource(id = R.string.sign_up_with), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                GoogleSocialButton(onClick = {})
            }
        }
    }

    if(state.errorMessage != null){
        Toast.makeText(LocalContext.current, state.errorMessage, Toast.LENGTH_LONG).show()
    }

}


@Preview(showBackground = true)
@Composable
fun SignInPreview(){
    FirebaseChatTheme {
        SignUpScreen(
            state = SignUpState(),
            onNameValueChange = {},
            onEmailValueChange = {},
            onPasswordValueChange = {},
            onRepeatPasswordValueChange = {},
            onVisibilityPassword = {},
            onVisibilityRepeatPassword = {},
            onNavigateToBack = {},
            onSignUp = {},
            onSuccess = {}
        )
    }
}