package com.atakavuncu.booktime.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.atakavuncu.booktime.BookTimeApp
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.data.model.user.Update
import com.atakavuncu.booktime.ui.auth.LoginScreen
import com.atakavuncu.booktime.ui.auth.RegisterScreen
import com.atakavuncu.booktime.ui.bookdetail.BookDetailScreen
import com.atakavuncu.booktime.ui.books.BooksScreen
import com.atakavuncu.booktime.ui.components.BottomNavigationBar
import com.atakavuncu.booktime.ui.discover.DiscoverScreen
import com.atakavuncu.booktime.ui.menu.AboutUsScreen
import com.atakavuncu.booktime.ui.menu.DeleteAccountScreen
import com.atakavuncu.booktime.ui.menu.FAQDetailScreen
import com.atakavuncu.booktime.ui.menu.LanguageScreen
import com.atakavuncu.booktime.ui.menu.LogOutScreen
import com.atakavuncu.booktime.ui.menu.MenuScreen
import com.atakavuncu.booktime.ui.menu.TextScreen
import com.atakavuncu.booktime.ui.menu.ThemeScreen
import com.atakavuncu.booktime.ui.menu.UpdateScreen
import com.atakavuncu.booktime.ui.profile.ProfileScreen
import com.atakavuncu.booktime.ui.profile.all_books.AllBooksScreen
import com.atakavuncu.booktime.ui.profile.favorites.FavoritesScreen
import com.atakavuncu.booktime.ui.profile.lists.ListsScreen
import com.atakavuncu.booktime.utils.SessionManager

@Composable
fun NavGraph(navController: NavHostController, sessionManager: SessionManager, startRoute: String) {
    fun AnimatedContentTransitionScope<*>.slideInFromLeft(durationMillis: Int = 1000) =
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(durationMillis))

    fun AnimatedContentTransitionScope<*>.slideOutToLeft(durationMillis: Int = 1000) =
        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(durationMillis))

    fun AnimatedContentTransitionScope<*>.slideInFromRight(durationMillis: Int = 1000) =
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(durationMillis))

    fun AnimatedContentTransitionScope<*>.slideOutToRight(durationMillis: Int = 1000) =
        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(durationMillis))

    NavHost(navController = navController, startDestination = startRoute) {
        composable(Route.MAIN) {
            BookTimeApp(
                sessionManager = sessionManager,
                navController = navController
            )
        }
        composable(Route.AUTH) {
            AuthNavGraph(navController, sessionManager, Route.LOGIN)
        }
        composable(
            route = Route.MENU,
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromRight() }
        ) { MenuScreen(stringResource(id = R.string.menu), navController) }
        composable(Route.BOOKS) { BooksScreen(navController, sessionManager) }
        composable(Route.DISCOVER) { DiscoverScreen(navController) }
        composable(Route.PROFILE) { ProfileScreen(navController, sessionManager) }
        composable(Route.ALL_BOOKS) { AllBooksScreen(navController, sessionManager) }
        composable(Route.FAVORITES) { FavoritesScreen(navController, sessionManager) }
        composable(Route.LISTS) { ListsScreen() }
        composable(Route.BOOK_DETAIL) {
            val bookId = it.arguments?.getString("bookId") ?: ""
            BookDetailScreen(navController = navController, bookId = bookId)
        }
        composable(
            route = Route.ACCOUNT_SETTINGS,
            enterTransition = { slideInFromLeft() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromRight() },
            popExitTransition = { slideOutToRight() },
        ) { MenuScreen(stringResource(id = R.string.account_settings), navController) }
        composable(
            route = Route.EMAIL_UPDATE,
            enterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { UpdateScreen(navController, Update.EMAIL) }
        composable(
            route = Route.PASSWORD_UPDATE,
            enterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { UpdateScreen(navController, Update.PASSWORD) }
        composable(
            route = Route.DELETE_ACCOUNT,
            enterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { DeleteAccountScreen(navController, sessionManager) }
        composable(
            route = Route.APP_SETTINGS,
            enterTransition = { slideInFromLeft() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromRight() },
            popExitTransition = { slideOutToRight() }
        ) { MenuScreen(stringResource(id = R.string.app_settings), navController) }
        composable(
            route = Route.LANGUAGE_SETTINGS,
            enterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { LanguageScreen(navController, sessionManager) }
        composable(
            route = Route.THEME_SETTINGS,
            enterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { ThemeScreen(navController, sessionManager) }
        composable(
            route = Route.PRIVACY_AND_SAFETY,
            enterTransition = { slideInFromLeft() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromRight() },
            popExitTransition = { slideOutToRight() }
        ) { MenuScreen(stringResource(id = R.string.privacy_and_safety), navController) }
        composable(
            route = Route.KVKK,
            enterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { TextScreen(stringResource(id = R.string.kvkk), navController) }
        composable(
            route = Route.PRIVACY_POLICY,
            enterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { TextScreen(stringResource(id = R.string.privacy_policy), navController) }
        composable(
            route = Route.FAQ,
            enterTransition = { slideInFromLeft() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromRight() },
            popExitTransition = { slideOutToRight() }
        ) { MenuScreen(stringResource(id = R.string.faq), navController) }
        composable(
            route = Route.FAQ_ACCOUNT_AND_PROFILE,
            enterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { FAQDetailScreen(stringResource(id = R.string.faq_account_and_profile), navController) }
        composable(
            route = Route.FAQ_BOOKS,
            enterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { FAQDetailScreen(stringResource(id = R.string.faq_books), navController) }
        composable(
            route = Route.FAQ_SETTINGS,
            enterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { FAQDetailScreen(stringResource(id = R.string.faq_settings), navController) }
        composable(
            route = Route.ABOUT_US,
            enterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { AboutUsScreen(navController) }
        composable(
            route = Route.LOG_OUT,
            enterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { LogOutScreen(navController, sessionManager) }
    }
}
