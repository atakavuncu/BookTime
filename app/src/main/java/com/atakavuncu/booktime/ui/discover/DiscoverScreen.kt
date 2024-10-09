package com.atakavuncu.booktime.ui.discover

import SearchBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.ui.theme.BookTimeTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.atakavuncu.booktime.ui.components.BookCard
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.Black
import kotlinx.coroutines.delay

@Composable
fun DiscoverScreen(
    navController: NavHostController,
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    val books by viewModel.books.collectAsState()
    var showLoading by remember { mutableStateOf(true) }
    var showNoResults by remember { mutableStateOf(false) }
    var emptyQuery by remember { mutableStateOf(true) }
    var query by rememberSaveable { mutableStateOf("") }
    var debouncedQuery by remember { mutableStateOf(query) }

    LaunchedEffect(query) {
        delay(500)
        debouncedQuery = query
    }

    LaunchedEffect(debouncedQuery) {
        if (debouncedQuery.isNotEmpty()) {
            showLoading = true
            viewModel.searchBooks(debouncedQuery)
            delay(3000)
            showLoading = false
            showNoResults = books.isEmpty()
            emptyQuery = false
        } else {
            emptyQuery = true
        }
    }

    BookTimeTheme {
        Scaffold(
            topBar = {
                SearchBox(
                    hint = if (emptyQuery) "Ara..." else query,
                    onSearch = { query = it }
                )
            },
            content = { paddingValues ->
                LazyColumn(contentPadding = paddingValues) {
                    when {
                        emptyQuery -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Hangi kitabı aramıştınız?", color = Black)
                                }
                            }
                        }

                        showLoading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = Black)
                                }
                            }
                        }

                        showNoResults -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Sonuç Bulunamadı", color = Black)
                                }
                            }
                        }

                        else -> {
                            items(books) { book ->
                                BookCard(book, navController)
                            }
                        }
                    }
                }
            },
            containerColor = BackgroundWhite
        )
    }
}