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
        enterTransition = {
            fadeIn(
                animationSpec = tween(300)
            ) + androidx.compose.animation.slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(300)
            ) + androidx.compose.animation.slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth / 4 },
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            fadeIn(
                animationSpec = tween(300)
            ) + androidx.compose.animation.slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth / 4 },
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(300)
            ) + androidx.compose.animation.slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            )
        }
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
