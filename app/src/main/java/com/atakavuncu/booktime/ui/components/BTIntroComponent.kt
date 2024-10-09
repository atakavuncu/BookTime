package com.atakavuncu.booktime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.data.model.book.ExistingBook
import com.atakavuncu.booktime.ui.navigation.Route
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.Blue
import com.atakavuncu.booktime.ui.theme.TextBlack
import com.atakavuncu.booktime.ui.theme.White

@Composable
fun BTIntroComponent(
    title: String,
    itemsList: List<ExistingBook>?,
    seeAllScreenRoute: String,
    navController: NavHostController
) {
    Column(
        modifier = Modifier.padding(start = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, color = Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = BackgroundWhite),
                modifier = Modifier.padding(0.dp),
                shape = RoundedCornerShape(percent = 0),
                onClick = {
                    navController.navigate(seeAllScreenRoute)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.see_all),
                    color = Blue,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        BTSpace(spaceHeight = 5)
        LazyRow(
            modifier = Modifier.fillMaxSize()
        ) {
            if (itemsList != null){
                if (itemsList.isNotEmpty()) {
                    items(itemsList) { item ->
                        Button(
                            onClick = { navController.navigate("book-detail/${item.bookId}") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .width(90.dp)
                                .padding(end = 5.dp),
                            shape = RoundedCornerShape(percent = 8),
                            colors = ButtonDefaults.buttonColors(containerColor = White),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            AsyncImage(
                                model = item.coverUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .width(90.dp)
                            )
                        }
                    }
                } else {
                    item {
                        Text(
                            stringResource(id = R.string.have_not_added_any_book),
                            style = TextStyle(color = TextBlack)
                        )
                    }
                }
            } else {
                item {
                    Text(
                        stringResource(id = R.string.have_not_added_any_book),
                        style = TextStyle(color = TextBlack)
                    )
                }
            }
        }
    }
}