package com.luceno.firebasechat

import androidx.navigation.NavHostController

class FirebaseAppState(
    val navController: NavHostController
) {

    fun navigate(route: String) {
        navController.navigate(route)
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route){
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

}