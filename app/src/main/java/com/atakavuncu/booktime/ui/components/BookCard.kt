package com.atakavuncu.booktime.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.atakavuncu.booktime.data.model.book.Book
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.TextBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCard(book: Book, navController: NavHostController) {
    Card(
        modifier = Modifier.padding(8.dp),
        onClick = {
            navController.navigate("book-detail/${book.id}")
        },
        colors = CardDefaults.cardColors(
            containerColor = BackgroundWhite,
            contentColor = TextBlack
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = book.coverUrl,
                contentDescription = book.title,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                book.title?.let { Text(text = it, style = MaterialTheme.typography.headlineSmall) }
                book.authorName?.let {
                    Text(
                        text = it.joinToString(", "),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}