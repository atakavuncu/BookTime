package com.atakavuncu.booktime.ui.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.atakavuncu.booktime.R
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.data.model.menu.MenuItem

@Composable
fun MenuScreen(
    title: String,
    navController: NavHostController
) {
    var menuItems by remember { mutableStateOf(emptyList<MenuItem>()) }

    when(title) {
        stringResource(id = R.string.menu) -> {
            menuItems = MenuItemProvider.getMainMenuItems(navController)
        }
        stringResource(id = R.string.account_settings) -> {
            menuItems = MenuItemProvider.getAccountSettingsItems(navController)
        }
        stringResource(id = R.string.app_settings) -> {
            menuItems = MenuItemProvider.getAppSettingsItems(navController)
        }
        stringResource(id = R.string.privacy_and_safety) -> {
            menuItems = MenuItemProvider.getPrivacyAndSafetyItems(navController)
        }
        stringResource(id = R.string.faq) -> {
            menuItems = MenuItemProvider.getFAQItems(navController)
        }

    }

    MenuContent(
        title = title,
        backButtonEnable = title != stringResource(id = R.string.menu),
        menuItems = menuItems,
        navController = navController
    )
}
