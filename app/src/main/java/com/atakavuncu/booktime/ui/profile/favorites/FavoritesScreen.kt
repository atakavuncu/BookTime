package com.atakavuncu.booktime.ui.profile.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.ui.auth.UserViewModel
import com.atakavuncu.booktime.ui.components.BTAppBar
import com.atakavuncu.booktime.ui.helper.BookCategory
import com.atakavuncu.booktime.ui.helper.BookGrid
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.utils.SessionManager
import kotlin.math.ceil

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    sessionManager: SessionManager,
    viewModel: UserViewModel = hiltViewModel()
) {
    val userId = sessionManager.getUserId()
    val user by viewModel.user.collectAsState()

    if (userId != null) {
        viewModel.fetchUserData(userId)
    }

    Scaffold(
        topBar = {
            BTAppBar(title = stringResource(id = R.string.favorites), navController = navController)
        },
        containerColor = BackgroundWhite
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .verticalScroll(rememberScrollState())
        ) {
            user?.favoriteBooks?.let { books ->
                val gridRowCount: Double = ceil(books.size.toDouble() / 3)
                val gridHeight = gridRowCount * 168
                BookGrid(books = books, gridHeight = gridHeight, navController)
            }
        }
    }
}