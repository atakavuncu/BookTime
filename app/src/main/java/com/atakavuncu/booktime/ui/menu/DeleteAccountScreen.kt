package com.atakavuncu.booktime.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.ui.auth.UserViewModel
import com.atakavuncu.booktime.ui.components.BTAppBar
import com.atakavuncu.booktime.ui.components.BTTextField
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.Red
import com.atakavuncu.booktime.utils.SessionManager

@Composable
fun DeleteAccountScreen(
    navController: NavHostController,
    sessionManager: SessionManager,
    viewModel: UserViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val deleteAccountResult by viewModel.deleteAccountResult.collectAsState()
    var isLoggedOut by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    if (isLoggedOut){
        RestartApp()
    }

    LaunchedEffect(deleteAccountResult) {
        if (deleteAccountResult) {
            isError = true
            isLoggedOut = true
            sessionManager.setIsLoggedIn(false)
        } else if (email.isNotEmpty() && password.isNotEmpty()) {
            isError = true
        }
    }

    Scaffold(
        topBar = {
            BTAppBar(
                title = stringResource(id = R.string.delete_account),
                titleColor = Red,
                navController = navController,
                backButtonEnable = true
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundWhite)
                .padding(top = paddingValues.calculateTopPadding())
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            item {
                Text(
                    stringResource(id = R.string.delete_account_are_you_sure),
                    color = Black,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    stringResource(id = R.string.delete_account_warning),
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    stringResource(id = R.string.delete_account_warning_item),
                    color = Black,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    stringResource(id = R.string.delete_account_enter_info),
                    color = Black,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                BTTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = stringResource(id = R.string.email)
                )
                Spacer(modifier = Modifier.height(8.dp))
                BTTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = stringResource(id = R.string.password)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(
                        onClick = {
                            viewModel.deleteAccount(email, password)
                        },
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .width(150.dp)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Red),
                        shape = RoundedCornerShape(16)
                    ) {
                        Text(
                            stringResource(id = R.string.delete_account).uppercase(),
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (isError) {
                    Text(
                        text = stringResource(id = R.string.auth_error),
                        color = Color.Black
                    )
                }
            }
        }
    }
}