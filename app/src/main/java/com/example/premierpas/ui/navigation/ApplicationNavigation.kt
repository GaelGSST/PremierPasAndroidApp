package com.example.premierpas.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.premierpas.ui.screen.MainScreen
import com.example.premierpas.ui.screen.SecondScreen

@Composable
fun ApplicationNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = ApplicationNavigationPath.Home,
        enterTransition = { fadeIn(animationSpec = tween(700)) },
        exitTransition = { fadeOut(animationSpec = tween(700)) },
    ) {
        composable<ApplicationNavigationPath.Home> {
            MainScreen(
                onButtonClick = { navController.navigate(route = ApplicationNavigationPath.Second) }
            )
        }

        composable<ApplicationNavigationPath.Second> {
            SecondScreen(navigateBack = { navController.popBackStack() })
        }
    }
}

object ApplicationNavigationPath {
    @Serializable
    data object Home

    @Serializable
    data object Second
}
