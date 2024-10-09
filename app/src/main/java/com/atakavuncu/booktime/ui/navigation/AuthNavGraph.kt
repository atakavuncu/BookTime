package com.atakavuncu.booktime.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.atakavuncu.booktime.ui.auth.CreateProfileScreen
import com.atakavuncu.booktime.ui.auth.LoginScreen
import com.atakavuncu.booktime.ui.auth.RegisterScreen
import com.atakavuncu.booktime.ui.components.BottomNavigationBar
import com.atakavuncu.booktime.utils.SessionManager

@Composable
fun AuthNavGraph(navController: NavHostController, sessionManager: SessionManager, startRoute: String) {
    fun AnimatedContentTransitionScope<*>.slideInFromLeft(durationMillis: Int = 1000) =
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(durationMillis))

    fun AnimatedContentTransitionScope<*>.slideOutToLeft(durationMillis: Int = 1000) =
        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(durationMillis))

    fun AnimatedContentTransitionScope<*>.slideInFromRight(durationMillis: Int = 1000) =
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(durationMillis))

    fun AnimatedContentTransitionScope<*>.slideOutToRight(durationMillis: Int = 1000) =
        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(durationMillis))

    NavHost(navController = navController, startDestination = startRoute) {
        composable(
            route = Route.LOGIN,
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromRight() },
            popExitTransition = { slideOutToLeft() }
        ) { LoginScreen(navController, sessionManager) }
        composable(
            route = Route.REGISTER,
            enterTransition = { slideInFromLeft() },
            exitTransition = { slideOutToRight() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { RegisterScreen(navController) }
        composable(
            route = Route.CREATE_PROFILE,
            enterTransition = { slideInFromLeft() },
            exitTransition = { slideOutToRight() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { CreateProfileScreen(navController, sessionManager) }
        composable(route = Route.MAIN) { BottomNavigationBar(
            navHostController = navController,
            sessionManager = sessionManager
        ) }
    }
}