package com.blipblipcode.squaddemo.ui.graphNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blipblipcode.squaddemo.ui.home.HomeScreen
import com.blipblipcode.squaddemo.ui.start.StartScreen

@Composable
fun NavHosting() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Splash.route) {


        composable(route = Screens.Splash.route) {
            StartScreen { route ->
                navController.popBackStack()
                navController.navigate(route)
            }
        }
        composable(route = Screens.Home.route) {
            HomeScreen { route ->
                navController.navigate(route) {
                    popUpTo(Screens.Home.route)
                }
            }
        }

    }

}