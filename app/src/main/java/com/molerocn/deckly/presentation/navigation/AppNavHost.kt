package com.molerocn.deckly.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.molerocn.deckly.presentation.screens.add_note.AddNoteScreen
import com.molerocn.deckly.presentation.screens.home.HomeScreen
import com.molerocn.deckly.presentation.screens.login.LoginScreen
import com.molerocn.deckly.presentation.screens.profile.ProfileScreen
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
            LoginScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.STARTUP) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.HOME) {
            HomeScreen(
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }
        composable(Routes.ADD_NOTE) {
            AddNoteScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                onBack = {
                    navController.popBackStack()
                },
                navigateOnSignOut = {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }
    }
}
