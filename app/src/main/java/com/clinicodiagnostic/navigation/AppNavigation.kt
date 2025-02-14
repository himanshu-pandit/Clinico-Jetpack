package com.clinicodiagnostic.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.clinicodiagnostic.view.screens.HomeSceeen
import com.clinicodiagnostic.view.screens.OTPScreen
import com.clinicodiagnostic.view.screens.login.LoginScreen

@Composable
fun AppNavigation(controller: NavHostController){

    NavHost(navController = controller, startDestination = Screen.Login.route){
        composable(route = Screen.Login.route) {
            LoginScreen(controller)
        }
        composable(route = Screen.OTP.route) {
            OTPScreen(controller)
        }
        composable(route = Screen.Home.route){
            HomeSceeen(controller)
        }
    }

}