package com.luceno.firebasechat.feature_auth.presentation.signin

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.luceno.firebasechat.shared.composable.EmailField
import com.luceno.firebasechat.shared.composable.PasswordField

@Composable
fun SignInScreen(
    onSuccess: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(contract = StartActivityForResult()){
        viewModel.onResponseSignInWithGoogle(it, onSuccess)
    }

    LaunchedEffect(Unit){
        viewModel.checkIsSignIn(onSuccess)
    }

    Column(modifier = modifier) {
        EmailField(value = state.email, onValueChange = viewModel::onEmailChange, modifier = Modifier)
        PasswordField(value = state.password, onValueChange = viewModel::onPasswordChange, modifier = Modifier)
        TextButton(onClick = { viewModel.onSignInWithEmail(onSuccess) }) {
            Text(text = "Login")
        }
        Button(onClick = {  viewModel.onSignInWithGoogle(context) { launcher.launch(it) } }) {
            Text(text = "SignIn Google")
        }
        Button(onClick = { Firebase.auth.signOut() }) {
            Text(text = "Logout")
        }
    }

}
