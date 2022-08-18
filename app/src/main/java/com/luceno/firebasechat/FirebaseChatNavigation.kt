package com.luceno.firebasechat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luceno.firebasechat.feature_chat.presentation.ChatRoomScreen
import com.luceno.firebasechat.feature_auth.presentation.signin.SignInScreen
import com.luceno.firebasechat.firebase_splash.SplashScreen


@Composable
fun FirebaseChatApp(){

    val appState = rememberAppState()

    NavHost(
        navController = appState.navController,
        startDestination = SPLASH_SCREEN
    ){

        composable(SPLASH_SCREEN){
            SplashScreen(
                onLoad = {

                },
                onNavigateTo = {
                    appState.navController.popBackStack()
                    appState.navController.navigate(SIGN_IN_SCREEN)
                }
            )
        }

        composable(SIGN_IN_SCREEN) {
            SignInScreen( onSuccess = {
                appState.navigateAndPopUp(CHAT_ROOM_SCREEN, SIGN_IN_SCREEN)
            })
        }

        composable(CHAT_ROOM_SCREEN) {
            ChatRoomScreen(onSingOut = { appState.navigate(SIGN_IN_SCREEN) })
        }

//        composable("$CHAT_ROOM_SCREEN/{$USER_ID}",
//            arguments = listOf(
//                navArgument(USER_ID){ type = NavType.StringType}
//            )
//        )
//        { backStackEntry ->
//            val userId = backStackEntry.arguments?.getString(USER_ID)
//            ChatRoomScreen(onSingOut = { appState.navigate(SIGN_IN_SCREEN) })
//        }

    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    FirebaseAppState(navController)
}
