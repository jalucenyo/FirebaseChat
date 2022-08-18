package com.luceno.firebasechat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luceno.firebasechat.feature_chat.presentation.ChatRoomScreen
import com.luceno.firebasechat.feature_auth.presentation.signin.SignInScreen
import com.luceno.firebasechat.feature_auth.presentation.signin.SignInViewModel
import com.luceno.firebasechat.feature_auth.presentation.signup.SignUpScreen
import com.luceno.firebasechat.feature_auth.presentation.signup.SignUpViewModel
import com.luceno.firebasechat.firebase_splash.SplashScreen


@Composable
fun FirebaseChatApp(){

    val appState = rememberAppState()
    val context = LocalContext.current

    NavHost(
        navController = appState.navController,
        startDestination = SPLASH_SCREEN
    ){

        composable(route = SPLASH_SCREEN){
            SplashScreen(
                onLoad = {

                },
                onNavigateTo = {
                    appState.navController.popBackStack()
                    appState.navController.navigate(SIGN_IN_SCREEN)
                }
            )
        }

        composable(route = SIGN_IN_SCREEN) {
            val viewModel: SignInViewModel = hiltViewModel()
            SignInScreen(
                state = viewModel.state.value,
                onEmailValueChange = viewModel::onEmailChange,
                onPasswordValueChange = viewModel::onPasswordChange,
                onVisibilityPassword = viewModel::onPasswordVisibility,
                onSignInWithEmail = viewModel::onSignInWithEmail,
                onSignInWithGoogle = viewModel::onSignInWithGoogle,
                onResponseSignInWithGoogle = viewModel::onResponseSignInWithGoogle,
                checkIsSignIn = viewModel::checkIsSignIn,
                onSuccess = {
                    appState.navController.popBackStack()
                    appState.navController.navigate(CHAT_ROOM_SCREEN)
                },
                onNavigateToSignUp = { appState.navController.navigate(SIGN_UP_SCREEN) }
            )
        }

        composable(route = SIGN_UP_SCREEN) {
            val viewModel: SignUpViewModel = hiltViewModel()
            SignUpScreen(
                state = viewModel.state.value,
                onNavigateToBack = { appState.navController.popBackStack() },
                onNameValueChange = viewModel::onNameValueChange,
                onEmailValueChange = viewModel::onEmailValueChange,
                onPasswordValueChange = viewModel::onPasswordValueChange,
                onRepeatPasswordValueChange = viewModel::onRepeatPasswordValueChange,
                onVisibilityPassword = viewModel::onPasswordVisibility,
                onVisibilityRepeatPassword = viewModel::onRepeatPasswordVisibility,
                onSignUp = viewModel::onSignUp,
                onSuccess = {
                    appState.navController.navigate(CHAT_ROOM_SCREEN)
                }
            )
        }

        composable(route = CHAT_ROOM_SCREEN) {
            ChatRoomScreen(onSingOut = { appState.navController.navigate(SIGN_IN_SCREEN) })
        }

    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    FirebaseAppState(navController)
}
