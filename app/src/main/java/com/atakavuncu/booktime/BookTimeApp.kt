package com.atakavuncu.booktime

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.ui.auth.UserViewModel
import com.atakavuncu.booktime.ui.components.BottomNavigationBar
import com.atakavuncu.booktime.ui.components.BottomNavigationItem
import com.atakavuncu.booktime.ui.navigation.AuthNavGraph
import com.atakavuncu.booktime.ui.navigation.NavGraph
import com.atakavuncu.booktime.ui.navigation.Route
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.White
import com.atakavuncu.booktime.utils.SessionManager

@Composable
fun BookTimeApp(
    sessionManager: SessionManager,
    navController: NavHostController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    var isLoggedIn by remember { mutableStateOf(sessionManager.isLoggedIn()) }
    val loginResult by userViewModel.loginResult.collectAsState()
    val registerResult by userViewModel.registerResult.collectAsState()

    isLoggedIn = loginResult != null

    if (isLoggedIn) {
        BottomNavigationBar(navController, sessionManager)
    } else {
        AuthNavGraph(
            sessionManager = sessionManager,
            navController = navController,
            startRoute = Route.LOGIN
        )
    }

    LaunchedEffect(loginResult) {
        loginResult?.let { user ->
            sessionManager.saveUserSession(user)
            isLoggedIn = true
            Log.d("BookTimeApp", "Login successful: ${user.uid}")
        }
    }

    LaunchedEffect(registerResult) {
        if (registerResult != null) {
            loginResult?.let { user ->
                sessionManager.saveUserSession(user)
                isLoggedIn = true
                Log.d("BookTimeApp", "Register successful: ${user.uid}")
            }
        }
    }

    LaunchedEffect(sessionManager.isLoggedIn()) {
        isLoggedIn = sessionManager.isLoggedIn()
    }
}
