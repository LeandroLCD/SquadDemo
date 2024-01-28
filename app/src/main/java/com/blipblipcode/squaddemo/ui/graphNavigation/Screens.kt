package com.blipblipcode.squaddemo.ui.graphNavigation

sealed class Screens(val route: String) {

    data object Home : Screens("HomeScreen")

    data object Splash : Screens("SplashScreen")

}