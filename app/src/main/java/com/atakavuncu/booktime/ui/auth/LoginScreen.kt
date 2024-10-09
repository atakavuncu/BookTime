package com.atakavuncu.booktime.ui.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.MainActivity
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.ui.components.BTButton
import com.atakavuncu.booktime.ui.components.BTTextField
import com.atakavuncu.booktime.ui.components.BottomNavigationBar
import com.atakavuncu.booktime.ui.navigation.Route
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.utils.SessionManager
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    navController: NavHostController,
    sessionManager: SessionManager,
    userViewModel: UserViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoginError by remember { mutableStateOf(false) }
    val loginResult by userViewModel.loginResult.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    var isLoggedIn by remember { mutableStateOf(false) }

    LaunchedEffect(sessionManager.isLoggedIn()) {
        isLoggedIn = sessionManager.isLoggedIn()
        isLoading = false
    }
    Log.d("LoginScreen", "isLoading: $isLoading")
    Log.d("LoginScreen", "isLoggedIn: $isLoggedIn")

    LaunchedEffect(loginResult) {
        loginResult?.let { user ->
            isLoading = false
            sessionManager.saveUserSession(user)
            isLoginError = false
            navController.navigate(Route.MAIN) {
                popUpTo(Route.LOGIN) { inclusive = true }
            }
        }
    }

    LaunchedEffect(isLoading) {
        isLoginError = false
        delay(5000)
        if(email.isNotEmpty() && password.isNotEmpty()) { isLoginError = true }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Black)
        }
    } else if (isLoggedIn) {
        navController.navigate(Route.MAIN) {
            popUpTo(Route.LOGIN) { inclusive = true }
        }
    } else {
        Scaffold(
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundWhite)
                        .padding(vertical = paddingValues.calculateTopPadding())
                        .padding(top = 100.dp, start = 40.dp, end = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.booktime_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = stringResource(id = R.string.app_name),
                        color = Black,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    BTTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = stringResource(id = R.string.email),
                        onDone = { passwordFocusRequester.requestFocus() },
                        modifier = Modifier.focusRequester(emailFocusRequester)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    BTTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = stringResource(id = R.string.password),
                        isSecret = true,
                        onDone = {
                            userViewModel.loginUser(email, password)
                        },
                        modifier = Modifier.focusRequester(passwordFocusRequester)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (isLoginError) {
                        Text(
                            text = stringResource(id = R.string.auth_error),
                            color = Black,
                            fontSize = 10.sp,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    BTButton(
                        text = stringResource(id = R.string.login).uppercase(),
                        onClick = {
                            isLoading = true
                            userViewModel.loginUser(email, password)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        onClick = { navController.navigate(Route.REGISTER) },
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = BackgroundWhite,
                            contentColor = Black
                        )
                    ) {
                        Text(stringResource(id = R.string.do_not_have_account))
                    }
                }
            }
        )
    }
}

