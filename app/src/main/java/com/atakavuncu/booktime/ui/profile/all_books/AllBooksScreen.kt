package com.atakavuncu.booktime.ui.profile.all_books

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.utils.SessionManager

data class Book(
    val id: Int,
    val title: String,
    val imageRes: Int,
    val status: BookStatus
)

enum class BookStatus {
    READING, NOT_STARTED, READ, STOPPED
}

@Composable
fun AllBooksScreen(
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
            BTAppBar(title = stringResource(id = R.string.all_books), navController = navController)
        },
        containerColor = BackgroundWhite
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            user?.books?.let { books ->
                BookCategory(stringResource(id = R.string.reading), books.filter { it.status == "Okuyorum" }, navController)
                BookCategory(stringResource(id = R.string.not_started), books.filter { it.status == "Okumadım" }, navController)
                BookCategory(stringResource(id = R.string.read), books.filter { it.status == "Okudum" }, navController)
                BookCategory(stringResource(id = R.string.stopped), books.filter { it.status == "Durduruldu" }, navController)
            }
        }
    }
}
