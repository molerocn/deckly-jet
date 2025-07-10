package com.molerocn.deckly.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.molerocn.deckly.presentation.screens.startup.StartupScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Routes.STARTUP
    ) {
        composable(Routes.STARTUP) {
            StartupScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.STARTUP) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.LOGIN) {
            // LoginScreen()
        }
        composable(Routes.HOME) {
            // HomeScreen()
        }
    }
}
