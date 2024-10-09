package com.atakavuncu.booktime.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.data.model.menu.MenuItem
import com.atakavuncu.booktime.ui.components.BTAppBar
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.Red
import com.atakavuncu.booktime.ui.theme.StickGrey
import com.atakavuncu.booktime.utils.SessionManager

@Composable
fun MenuContent(
    title: String,
    backButtonEnable: Boolean = true,
    menuItems: List<MenuItem>,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            BTAppBar(
                title = title,
                navController = navController,
                backButtonEnable = backButtonEnable
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            item {
                Divider(
                    color = StickGrey,
                    thickness = 0.5.dp
                )
            }
            items(menuItems) { item ->
                MenuListItem(
                    item,
                    textColor =
                        if (item.title == stringResource(id = R.string.logout)
                            || item.title == stringResource(id = R.string.delete_account)
                        ) Red else Black
                )
            }
        }
    }
}