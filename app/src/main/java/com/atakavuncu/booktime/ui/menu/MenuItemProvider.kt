package com.atakavuncu.booktime.ui.menu

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.data.model.menu.MenuItem
import com.atakavuncu.booktime.ui.navigation.Route

object MenuItemProvider {

    @Composable
    fun getMainMenuItems(navController: NavHostController): List<MenuItem> {
        return listOf(
            MenuItem(stringResource(id = R.string.account_settings)){ navController.navigate(Route.ACCOUNT_SETTINGS) },
            MenuItem(stringResource(id = R.string.app_settings)) { navController.navigate(Route.APP_SETTINGS) },
            MenuItem(stringResource(id = R.string.privacy_and_safety)) { navController.navigate(Route.PRIVACY_AND_SAFETY) },
            MenuItem(stringResource(id = R.string.faq)) { navController.navigate(Route.FAQ) },
            MenuItem(stringResource(id = R.string.about_us)) { navController.navigate(Route.ABOUT_US) },
            MenuItem(stringResource(id = R.string.logout)) { navController.navigate(Route.LOG_OUT) }
        )
    }

    @Composable
    fun getAccountSettingsItems(navController: NavHostController): List<MenuItem> {
        return listOf(
            MenuItem(stringResource(id = R.string.email_update)) { navController.navigate(Route.EMAIL_UPDATE) },
            MenuItem(stringResource(id = R.string.password_update)) { navController.navigate(Route.PASSWORD_UPDATE) },
            MenuItem(stringResource(id = R.string.delete_account)) { navController.navigate(Route.DELETE_ACCOUNT) }
        )
    }

    @Composable
    fun getAppSettingsItems(navController: NavHostController): List<MenuItem> {
        return listOf(
            MenuItem(stringResource(id = R.string.language)) { navController.navigate(Route.LANGUAGE_SETTINGS) },
            MenuItem(stringResource(id = R.string.theme)) { navController.navigate(Route.THEME_SETTINGS) }
        )
    }

    @Composable
    fun getPrivacyAndSafetyItems(navController: NavHostController): List<MenuItem> {
        return listOf(
            MenuItem(stringResource(id = R.string.kvkk)) { navController.navigate(Route.KVKK) },
            MenuItem(stringResource(id = R.string.privacy_policy)) { navController.navigate(Route.PRIVACY_POLICY) }
        )
    }

    @Composable
    fun getFAQItems(navController: NavHostController): List<MenuItem> {
        return listOf(
            MenuItem(stringResource(id = R.string.faq_account_and_profile)) { navController.navigate(Route.FAQ_ACCOUNT_AND_PROFILE) },
            MenuItem(stringResource(id = R.string.faq_books)) { navController.navigate(Route.FAQ_BOOKS) },
            MenuItem(stringResource(id = R.string.faq_settings)) { navController.navigate(Route.FAQ_SETTINGS) }
        )
    }

}