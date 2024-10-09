package com.atakavuncu.booktime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.Blue
import com.atakavuncu.booktime.ui.theme.DarkGrey
import com.atakavuncu.booktime.ui.theme.White
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.ui.auth.UserViewModel
import com.atakavuncu.booktime.ui.navigation.NavGraph
import com.atakavuncu.booktime.ui.navigation.Route
import com.atakavuncu.booktime.utils.SessionManager

@Composable
fun BottomNavigationBar(
    navHostController: NavHostController,
    sessionManager: SessionManager,
    userViewModel: UserViewModel = hiltViewModel()
) {
    var selectedItem by remember { mutableIntStateOf(3) }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = White,
                contentPadding = PaddingValues(0.dp),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        BottomNavigationItem(
                            icon = Icons.Filled.Menu,
                            label = stringResource(id = R.string.menu),
                            selected = selectedItem == 0,
                            onClick = {
                                selectedItem = 0
                                navController.navigate(Route.MENU)
                            }
                        )
                        BottomNavigationItem(
                            icon = ImageVector.vectorResource(id = R.drawable.baseline_menu_book_24),
                            label = stringResource(id = R.string.my_books),
                            selected = selectedItem == 1,
                            onClick = {
                                selectedItem = 1
                                navController.navigate(Route.BOOKS)
                            }
                        )
                        BottomNavigationItem(
                            icon = Icons.Filled.Search,
                            label = stringResource(id = R.string.discover),
                            selected = selectedItem == 2,
                            onClick = {
                                selectedItem = 2
                                navController.navigate(Route.DISCOVER)
                            }
                        )
                        BottomNavigationItem(
                            icon = Icons.Filled.Person,
                            label = stringResource(id = R.string.profile),
                            selected = selectedItem == 3,
                            onClick = {
                                selectedItem = 3
                                navController.navigate(Route.PROFILE)
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        val statusBarInsets = WindowInsets.statusBars
        val navigationBarInsets = WindowInsets.navigationBars
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = statusBarInsets
                        .asPaddingValues()
                        .calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = innerPadding.calculateRightPadding(LayoutDirection.Ltr)
                )
                .background(BackgroundWhite),
            contentAlignment = Alignment.Center
        ) {
            NavGraph(
                sessionManager = sessionManager,
                navController = navController,
                startRoute = Route.PROFILE
            )
        }
    }
}

@Composable
fun BottomNavigationItem(icon: ImageVector, label: String, selected: Boolean, onClick: () -> Unit) {
    val iconColor = if (selected) Blue else DarkGrey
    val textColor = if (selected) Blue else DarkGrey
    val noRippleInteractionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .clickable(
                interactionSource = noRippleInteractionSource,
                indication = null,
                onClick = onClick
            )
            .padding(0.dp)
            .background(color = White, shape = RoundedCornerShape(0))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = label, tint = iconColor)
            Text(text = label, color = textColor, fontSize = 12.sp)
        }
    }
}
