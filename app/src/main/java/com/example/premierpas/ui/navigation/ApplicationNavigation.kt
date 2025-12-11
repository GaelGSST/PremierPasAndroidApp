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
import androidx.navigation.toRoute
import com.example.premierpas.ui.screen.AnimalDetailScreen
import com.example.premierpas.ui.screen.AnimalListScreen
import com.example.premierpas.ui.screen.FirebaseConfigScreen
import com.example.premierpas.ui.screen.MainScreen

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
                onNavigateToAnimalList = { navController.navigate(route = ApplicationNavigationPath.AnimalList) },
                onNavigateToFirebase = { navController.navigate(route = ApplicationNavigationPath.FirebaseConfig) }
            )
        }

        composable<ApplicationNavigationPath.AnimalList> {
            AnimalListScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateToDetail = { detail -> navController.navigate(detail) }
            )
        }

        composable<ApplicationNavigationPath.FirebaseConfig> {
            FirebaseConfigScreen(navigateBack = { navController.popBackStack() })
        }

        composable<ApplicationNavigationPath.AnimalDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<ApplicationNavigationPath.AnimalDetail>()
            AnimalDetailScreen(
                animalId = args.id,
                binomialName = args.binomialName,
                commonName = args.commonName,
                location = args.location,
                wikiLink = args.wikiLink,
                lastRecord = args.lastRecord,
                imageSrc = args.imageSrc,
                shortDesc = args.shortDesc,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}

object ApplicationNavigationPath {
    @Serializable
    data object Home

    @Serializable
    data object AnimalList

    @Serializable
    data object FirebaseConfig

    @Serializable
    data class AnimalDetail(
        val id: Long,
        val binomialName: String,
        val commonName: String,
        val location: String,
        val wikiLink: String,
        val lastRecord: String,
        val imageSrc: String,
        val shortDesc: String
    )
}
