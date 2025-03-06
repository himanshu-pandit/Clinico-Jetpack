package com.clinicodiagnostic.navigation

sealed class Screen(val route: String){
    data object Login: Screen("login")
    data object OTP: Screen("otp/{otpNumber}")
    data object Home: Screen("home")
}