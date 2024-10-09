package com.atakavuncu.booktime.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.ui.auth.UserViewModel
import com.atakavuncu.booktime.ui.components.BTInfoBox
import com.atakavuncu.booktime.ui.components.BTSpace
import com.atakavuncu.booktime.ui.components.BTIntroComponent
import com.atakavuncu.booktime.ui.navigation.Route
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.Grey
import com.atakavuncu.booktime.ui.theme.TextBlack
import com.atakavuncu.booktime.ui.theme.White
import com.atakavuncu.booktime.utils.SessionManager

@Composable
fun ProfileScreen(
    navController: NavHostController,
    sessionManager: SessionManager,
    viewModel: UserViewModel = hiltViewModel()
) {
    val userId = sessionManager.getUserId()
    val user by viewModel.user.collectAsState()
    val readBookCount by viewModel.bookCount.collectAsState()
    val readPageCount by viewModel.pageCount.collectAsState()

    if (userId != null) {
        viewModel.fetchUserData(userId)
        viewModel.getReadBookCount(userId)
        viewModel.getReadPageCount(userId)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(BackgroundWhite),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                AsyncImage(
                    model = user?.coverPhotoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                AsyncImage(
                    model = user?.profilePhotoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .offset(y = 150.dp)
                        .clip(CircleShape)
                        .border(5.dp, White, CircleShape),
                    alignment = Alignment.Center
                )
            }
            BTSpace(spaceHeight = 5)
            user?.username?.let {
                Text(
                    text = it,
                    color = Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Grey),
                onClick = { navController.navigate(Route.CREATE_PROFILE) }
            ) {
                Text(
                    text = stringResource(id = R.string.edit),
                    color = TextBlack,
                    fontWeight = FontWeight.SemiBold
                )
            }
            BTSpace(spaceHeight = 5)
            Row {
                BTInfoBox(
                    title = stringResource(id = R.string.read_books),
                    contentText = readBookCount.toString(),
                    width = 150,
                    height = 60
                )
                Spacer(modifier = Modifier.width(20.dp))
                BTInfoBox(
                    title = stringResource(id = R.string.page_count),
                    contentText = readPageCount.toString(),
                    width = 150,
                    height = 60
                )
            }
            BTSpace(spaceHeight = 10)
            BTIntroComponent(
                title = stringResource(id = R.string.all_books),
                itemsList = user?.books,
                seeAllScreenRoute = Route.ALL_BOOKS,
                navController = navController
            )
            BTSpace(spaceHeight = 10)
            BTIntroComponent(
                title = stringResource(id = R.string.favorites),
                itemsList = user?.favoriteBooks,
                seeAllScreenRoute = Route.FAVORITES,
                navController = navController
            )
            BTSpace(spaceHeight = 20)
        }
    }
}
