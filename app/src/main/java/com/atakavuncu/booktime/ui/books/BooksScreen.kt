package com.atakavuncu.booktime.ui.books

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.ui.auth.UserViewModel
import com.atakavuncu.booktime.ui.components.BTSpace
import com.atakavuncu.booktime.ui.components.BookListView
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.utils.SessionManager

@Composable
fun BooksScreen(
    navController: NavHostController,
    sessionManager: SessionManager,
    viewModel: UserViewModel = hiltViewModel()
) {
    val userId = sessionManager.getUserId()
    val user by viewModel.user.collectAsState()

    fun refreshUserData() {
        if (userId != null) {
            viewModel.fetchUserData(userId)
        }
    }

    if (userId != null) {
        viewModel.fetchUserData(userId)
    }

    LazyColumn(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            user?.books?.filter { it.status == "Okuyorum" }?.let {
                if (it.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.keep_reading),
                        color = Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    BTSpace(spaceHeight = 5)
                    BookListView(
                        books = it,
                        navController = navController,
                        onBookStatusChanged = { refreshUserData() }
                    )
                }
            }
        }

        item {
            user?.books?.filter { it.status == "Okumadım" }?.let {
                BTSpace(spaceHeight = 15)
                if (it.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.start_books),
                        color = Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    BTSpace(spaceHeight = 5)
                    BookListView(
                        books = it,
                        navController = navController,
                        onBookStatusChanged = { refreshUserData() }
                    )
                }
            }
        }

        item {
            user?.books?.filter { it.status == "Durduruldu" }?.let {
                BTSpace(spaceHeight = 15)
                if (it.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.pick_up_the_threads),
                        color = Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    BTSpace(spaceHeight = 5)
                    BookListView(
                        books = it,
                        navController = navController,
                        onBookStatusChanged = { refreshUserData() }
                    )
                }
            }
        }
    }
}