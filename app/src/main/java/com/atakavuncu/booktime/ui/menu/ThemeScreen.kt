package com.atakavuncu.booktime.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.ui.components.BTAppBar
import com.atakavuncu.booktime.ui.navigation.Route
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.Red
import com.atakavuncu.booktime.utils.SessionManager

@Composable
fun ThemeScreen(
    navController: NavHostController,
    sessionManager: SessionManager
) {
    Scaffold(
        topBar = {
            BTAppBar(
                title = stringResource(id = R.string.theme),
                navController = navController,
                backButtonEnable = false
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(id = R.string.will_be_active), color = Black)
        }
    }
}