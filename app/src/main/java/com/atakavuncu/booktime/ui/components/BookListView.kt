package com.atakavuncu.booktime.ui.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.atakavuncu.booktime.data.model.book.BookDetails
import com.atakavuncu.booktime.data.model.book.ExistingBook
import com.atakavuncu.booktime.ui.bookdetail.BookDetailViewModel
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.Blue
import kotlinx.coroutines.delay

@SuppressLint("MutableCollectionMutableState")
@Composable
fun BookListView(
    books: List<ExistingBook> = emptyList(),
    navController: NavHostController,
    viewModel: BookDetailViewModel = hiltViewModel(),
    onBookStatusChanged: (() -> Unit)? = null
) {
    var bookUpdating by remember { mutableStateOf(false) }
    var updatingBookId by remember { mutableStateOf("") }

    Column {
        books.forEach { book ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.navigate("book-detail/${book.bookId}") },
                    modifier = Modifier
                        .height(64.dp)
                        .width(64.dp),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    AsyncImage(
                        model = book.coverUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.weight(2f)
                ) {
                    Text(
                        text = stringResource(id = R.string.page_start)
                                + book.readPageCount?.plus(1)
                                + stringResource(id = R.string.page_end),
                        fontSize = 14.sp,
                        color = Blue,
                        fontWeight = FontWeight.Bold
                    )

                    book.title?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            color = Black,
                            fontSize = 12.sp
                        )
                    }

                    Text(
                        text = book.authorName,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

                IconButton(
                    onClick = {
                        val updatedBook = ExistingBook(
                            bookId = book.bookId,
                            coverUrl = book.coverUrl,
                            title = book.title,
                            authorName = book.authorName,
                            bookPageCount = book.bookPageCount,
                            readPageCount = book.readPageCount?.plus(1),
                            status = if (book.readPageCount?.plus(1) == book.bookPageCount)
                                "Okudum"
                            else if (book.status == "Okumadım" || book.status == "Durduruldu")
                                "Okuyorum"
                            else
                                book.status
                        )
                        bookUpdating = true
                        updatingBookId = book.bookId!!
                        viewModel.updateBook(updatedBook) { success ->
                            if (success) {
                                onBookStatusChanged?.invoke()
                                bookUpdating = false
                                updatingBookId = ""
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = if (bookUpdating && book.bookId == updatingBookId)
                                R.drawable.baseline_check_circle_24
                            else
                                R.drawable.baseline_check_circle_outline_24
                        ),
                        contentDescription = null,
                        tint = Black,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}