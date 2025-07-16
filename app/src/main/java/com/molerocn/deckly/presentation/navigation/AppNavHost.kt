package com.molerocn.deckly.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.molerocn.deckly.presentation.screens.add_note.AddOrEditCardScreen
import com.molerocn.deckly.presentation.screens.deck_detail.DeckDetailScreen
import com.molerocn.deckly.presentation.screens.study_card.ReviewCardScreen
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
        composable(
            route = "${Routes.ADD_NOTE}?cardId={cardId}&deckId={deckId}&front={front}&back={back}",

            arguments = listOf(
                navArgument("cardId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("deckId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("front") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("back") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
            )
        ) { backStackEntry ->
            val deckId = backStackEntry.arguments?.getInt("deckId") ?: 0
            AddOrEditCardScreen(
                deckId = deckId,
                onBack = {
                    navController.popBackStack()
                },
                navController = navController,
                onBackAfterInsert = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
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
        composable(
            "${Routes.DECK_DETAIL}/{deckId}/{name}/{description}/{mountCards}",
            arguments = listOf(
                navArgument("deckId") { type = NavType.IntType },
                navArgument("name") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
                navArgument("mountCards") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val deckId = backStackEntry.arguments?.getInt("deckId") ?: 0
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val mountCards = backStackEntry.arguments?.getInt("mountCards") ?: 0
            val description = backStackEntry.arguments?.getString("description") ?: ""

            DeckDetailScreen(
                deckId = deckId,
                name = name,
                description = description,
                mountCards = mountCards,
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }
        composable(
            "${Routes.REVIEW_CARD}/{deckId}",
            arguments = listOf(
                navArgument("deckId") { type = NavType.IntType },
            )
        ) { backStackEntry ->

            ReviewCardScreen(
                onNavigate = { route ->
                    navController.navigate(route)
                },
                onBack = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
            )
        }
    }
}
