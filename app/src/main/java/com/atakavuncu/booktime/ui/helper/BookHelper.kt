package com.atakavuncu.booktime.ui.helper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.atakavuncu.booktime.data.model.book.ExistingBook
import com.atakavuncu.booktime.ui.components.BTLabel
import com.atakavuncu.booktime.ui.components.BTSpace
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import kotlin.math.ceil

@Composable
fun BookCategory(title: String, books: List<ExistingBook>, navController: NavHostController) {
    if (books.isNotEmpty()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BTLabel(text = title)
            BTSpace(spaceHeight = 8)
            val gridRowCount: Double = ceil(books.size.toDouble() / 3)
            val gridHeight =  gridRowCount * 168
            BookGrid(books = books, gridHeight = gridHeight, navController)
        }
    }
}

@Composable
fun BookItem(book: ExistingBook, navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .width(100.dp)
    ) {
        Button(
            onClick = { navController.navigate("book-detail/${book.bookId}") },
            shape = RoundedCornerShape(5.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BackgroundWhite)
        ) {
            AsyncImage(
                model = book.coverUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp))
            )
        }
    }
}

@Composable
fun BookGrid(
    books: List<ExistingBook>,
    gridHeight: Double,
    navController: NavHostController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.heightIn(max = gridHeight.dp),
        userScrollEnabled = false
    ) {
        items(books.size) { index ->
            BookItem(book = books[index], navController)
        }
    }
}