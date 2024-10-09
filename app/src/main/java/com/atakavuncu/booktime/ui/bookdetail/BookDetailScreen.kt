package com.atakavuncu.booktime.ui.bookdetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.data.model.book.ExistingBook
import com.atakavuncu.booktime.ui.components.BTIconText
import com.atakavuncu.booktime.ui.components.BTRadioButtons
import com.atakavuncu.booktime.ui.components.BTSpace
import com.atakavuncu.booktime.ui.components.PageNumberPicker
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.BookTimeTheme
import com.atakavuncu.booktime.ui.theme.Pink
import com.atakavuncu.booktime.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun BookDetailScreen(
    navController: NavHostController,
    viewModel: BookDetailViewModel = hiltViewModel(),
    bookId: String
) {
    val bookDetail by viewModel.book.collectAsState()
    var showLoading by remember { mutableStateOf(true) }
    var showNoResults by remember { mutableStateOf(true) }
    val currentBookDetail by rememberUpdatedState(bookDetail)
    var selectedPageNumber by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf("Okumadım") }
    val options = listOf("Okudum", "Okuyorum", "Durduruldu", "Okumadım")
    val existingBook by viewModel.existingBook.collectAsState()
    var isExists by remember { mutableStateOf(false) }
    var isEdited by remember { mutableStateOf(false) }
    val isFavorite by viewModel.isFavorite.collectAsState()

    LaunchedEffect(selectedOption, selectedPageNumber) {
        existingBook?.let { book ->
            isEdited = (selectedOption != book.status || selectedPageNumber != book.readPageCount)
        }
    }

    LaunchedEffect(bookId) {
        viewModel.getBookDetails(bookId)
        viewModel.getExistingBookById(bookId)
        viewModel.isBookFavorite(bookId)
    }

    LaunchedEffect(Unit) {
        delay(10000)
        showLoading = false
        if (currentBookDetail?.description?.description == "") {
            showNoResults = true
        }
    }

    LaunchedEffect(existingBook) {
        existingBook?.let { book ->
            book.status?.let { selectedOption = it }
            book.readPageCount?.let { selectedPageNumber = it }
            isExists = true

            Log.d("BookDetailScreen", "existingBook: $existingBook")
            Log.d("BookDetailScreen", "isExist: $isExists")
        }
    }

    LaunchedEffect(bookDetail?.description?.description) {
        if (bookDetail?.description?.description != "") {
            showLoading = false
            showNoResults = false
        }
    }

    BookTimeTheme {
        Scaffold(
            content = { paddingValues ->
                LazyColumn(
                    modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(210.dp)
                        ) {
                            AsyncImage(
                                model = bookDetail?.book?.coverUrl,
                                contentDescription = bookDetail?.book?.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(210.dp)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Black
                                            ),
                                            startY = 100f
                                        )
                                    )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                IconButton(
                                    onClick = { navController.popBackStack() }
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back),
                                        contentDescription = stringResource(id = R.string.back),
                                        modifier = Modifier.size(16.dp),
                                        tint = White,
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        if (isFavorite == true) {
                                            viewModel.deleteFavoriteBook(bookId)
                                        } else {
                                            viewModel.addFavoriteBook(
                                                newBook = ExistingBook(
                                                    bookId = bookId,
                                                    readPageCount = selectedPageNumber,
                                                    coverUrl = bookDetail?.book?.coverUrl,
                                                    status = selectedOption
                                                )
                                            )
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(
                                            id = if (isFavorite == true)
                                                R.drawable.baseline_favorite_24
                                            else
                                                R.drawable.baseline_favorite_border_24
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = if (isFavorite == true) Pink else White
                                    )
                                }
                            }
                            bookDetail?.book?.title?.let {
                                Text(
                                    text = it,
                                    color = White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(
                                            start = 16.dp,
                                            bottom = paddingValues.calculateTopPadding()
                                        )
                                )
                                Log.e("bookDetail", bookDetail!!.book.id)
                                bookDetail!!.book.coverUrl?.let { it1 -> Log.e("bookDetail", it1) }
                            }
                            bookDetail?.book?.authorName?.let {
                                Text(
                                    text = it.joinToString(", "),
                                    color = White,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(start = 16.dp, bottom = 8.dp)
                                )
                            }
                        }
                        Column(
                            Modifier.padding(
                                top = 24.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.information),
                                color = Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Column(
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(bottom = 12.dp)
                                ) {
                                    BTIconText(
                                        iconVector = ImageVector.vectorResource(id = R.drawable.page_count_24),
                                        text = bookDetail?.book?.pageCount.toString() + " " + stringResource(
                                            id = R.string.pages
                                        )
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .padding(bottom = 12.dp)
                                ) {
                                    BTIconText(
                                        iconVector = ImageVector.vectorResource(id = R.drawable.calendar_24),
                                        text = bookDetail?.book?.year.toString()
                                    )
                                }

                            }
                            Text(
                                text = stringResource(id = R.string.reading_status),
                                color = Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            BTRadioButtons(
                                options = options,
                                selectedOption = selectedOption,
                                onOptionSelected = { selectedOption = it }
                            )
                            BTSpace(spaceHeight = 5)
                            bookDetail?.book?.pageCount?.let { pageCount ->
                                PageNumberPicker(
                                    range = 0..pageCount,
                                    selectedOption,
                                    selectedPageNumber
                                ) {
                                    selectedPageNumber = it
                                }
                            }
                            if (!showNoResults) {
                                Text(
                                    text = stringResource(id = R.string.description),
                                    color = Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                bookDetail?.description?.description?.let {
                                    Text(
                                        text = it,
                                        color = Black,
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            },
            bottomBar = {
                Button(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Black),
                    shape = RoundedCornerShape(0.dp),
                    onClick = {
                        val newBook = ExistingBook(
                            bookId = bookId,
                            readPageCount = selectedPageNumber,
                            bookPageCount = bookDetail?.book?.pageCount,
                            title = bookDetail?.book?.title,
                            authorName = bookDetail?.book?.authorName?.joinToString(", ")!!,
                            coverUrl = bookDetail?.book?.coverUrl,
                            status = selectedOption
                        )
                        if (isEdited) {
                            viewModel.updateBook(newBook)
                        } else if (isExists) {
                            viewModel.deleteBook(bookId)
                        } else {
                            viewModel.addNewBook(newBook)
                        }
                        viewModel.getExistingBookById(bookId)
                    }
                ) {
                    Text(
                        text = if (isEdited)
                            stringResource(id = R.string.save).uppercase()
                        else if (isExists)
                            stringResource(id = R.string.delete).uppercase()
                        else
                            stringResource(id = R.string.add).uppercase(),
                        color = White,
                        fontSize = 16.sp
                    )
                }
            },
            containerColor = BackgroundWhite
        )
    }
}
