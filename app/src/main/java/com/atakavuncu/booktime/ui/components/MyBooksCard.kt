package com.atakavuncu.booktime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.atakavuncu.booktime.data.model.book.ExistingBook
import com.atakavuncu.booktime.ui.bookdetail.BookDetailViewModel
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.Blue

@Composable
fun MyBooksCard(
    book: ExistingBook,
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val bookDetail by viewModel.book.collectAsState()

    book.bookId?.let {
        viewModel.getBookDetails(it)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = book.coverUrl,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Sayfa Bilgisi
            Text(
                text = "${book.readPageCount?.plus(1)}. Sayfa",
                fontSize = 14.sp,
                color = Blue,
                fontWeight = FontWeight.Bold
            )

            // Kitap Adı
            bookDetail?.book?.title?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    fontSize = 12.sp
                )
            }

            // Yazar Adı
            bookDetail?.book?.authorName?.let {
                Text(
                    text = it.joinToString(", "),
                    color = Gray,
                    fontSize = 12.sp
                )
            }
        }

        IconButton(
            onClick = {
                viewModel.updateBook(
                    ExistingBook(
                        bookId = book.bookId,
                        coverUrl = book.coverUrl,
                        title = book.title,
                        authorName = book.authorName,
                        readPageCount = book.readPageCount?.plus(1),
                        bookPageCount = book.bookPageCount,
                        status = if (book.readPageCount?.plus(1) == book.bookPageCount)
                                "Okudum"
                            else
                                book.status
                    )
                )

            }
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Black,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
