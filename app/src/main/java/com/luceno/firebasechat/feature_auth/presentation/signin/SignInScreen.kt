package com.luceno.firebasechat.feature_auth.presentation.signin

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.luceno.firebasechat.R
import com.luceno.firebasechat.shared.composable.EmailField
import com.luceno.firebasechat.shared.composable.GoogleSocialButton
import com.luceno.firebasechat.shared.composable.PasswordField
import com.luceno.firebasechat.shared.composable.TextBiColor
import com.luceno.firebasechat.shared.composable.TitleText
import com.luceno.firebasechat.ui.theme.FirebaseChatTheme


@Composable
fun SignInScreen(
    state: SignInState,
    onSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onVisibilityPassword: ()-> Unit,
    onSignInWithEmail: (onSuccess: () -> Unit) -> Unit,
    onSignInWithGoogle: (context: Context, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) -> Unit,
    onResponseSignInWithGoogle: (result: ActivityResult, onSuccess: () -> Unit) -> Unit,
    checkIsSignIn: (onSuccess: () -> Unit) -> Unit,
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(contract = StartActivityForResult()) {
        onResponseSignInWithGoogle(it, onSuccess)
    }

    LaunchedEffect(Unit) {
        checkIsSignIn(onSuccess)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){

        Image(
            painter = painterResource(id = R.drawable.ic_login_image_v2),
            contentDescription = "login image",
            modifier = Modifier.padding(bottom = 400.dp),
            contentScale = ContentScale.Crop)

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter)
        {
            ConstraintLayout(){

                val (surface, fab) = createRefs()

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .constrainAs(surface) {
                            bottom.linkTo(parent.bottom)
                        },
                    elevation = 16.dp,
                    color = Color.White,
                    shape = RoundedCornerShape( topStartPercent = 8, topEndPercent = 8)
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = stringResource(id = R.string.welcome),
                            style = MaterialTheme.typography.h4
                        )

                        TitleText(text = stringResource(id = R.string.sign_in_to_your_account))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            EmailField(
                                value = state.email,
                                onValueChange = onEmailValueChange)

                            PasswordField(
                                value = state.password,
                                visibility = state.isPasswordVisibility,
                                onValueChange = onPasswordValueChange,
                                onVisibilityClick = onVisibilityPassword,
                                modifier = Modifier.padding(top = 8.dp))

                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { onSignInWithEmail(onSuccess) },
                            ) {
                                Text(text = stringResource(id = R.string.sign_in))
                            }

                            GoogleSocialButton(onClick = { onSignInWithGoogle(context, launcher) } )

                            TextBiColor(
                                textStart = stringResource(id = R.string.dot_not_a_have_account),
                                textEnd = stringResource(id = R.string.sign_up),
                                onClick = { onNavigateToSignUp() }
                            )
                        }
                    }
                }

                FloatingActionButton(
                    modifier = Modifier
                        .size(72.dp)
                        .constrainAs(fab) {
                            top.linkTo(surface.top, margin = (-36).dp)
                            end.linkTo(surface.end, margin = 36.dp)
                        },
                    backgroundColor = MaterialTheme.colors.primary,
                    onClick = {}) {

                    Icon(
                        modifier = Modifier.size(42.dp),
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Forward Icon",
                        tint = Color.White
                    )
                }
            }
        }

    }

    if(state.errorMessage != null){
        Toast.makeText(context, state.errorMessage, Toast.LENGTH_LONG).show()
    }


}

@Preview(showBackground = true)
@Composable
fun SignInPreview(){
    FirebaseChatTheme() {
        SignInScreen(
            state = SignInState(),
            onSuccess = {},
            onNavigateToSignUp = {},
            onEmailValueChange = {},
            onPasswordValueChange = {},
            onVisibilityPassword = {},
            onSignInWithEmail = {},
            onSignInWithGoogle = { _, _ -> },
            onResponseSignInWithGoogle = { _, _ -> },
            checkIsSignIn = {}
        )
    }
}
