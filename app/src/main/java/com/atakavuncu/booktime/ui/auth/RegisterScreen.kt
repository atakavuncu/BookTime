package com.atakavuncu.booktime.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.ui.components.BTButton
import com.atakavuncu.booktime.ui.components.BTTextField
import com.atakavuncu.booktime.ui.navigation.Route
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.BookTimeTheme

@Composable
fun RegisterScreen(
    navController: NavHostController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var confirmResult by remember { mutableStateOf(true) }
    val registerResult by userViewModel.registerResult.collectAsState()

    if (registerResult != null) {
        navController.navigate(Route.CREATE_PROFILE)
    }

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundWhite)
                    .padding(vertical = paddingValues.calculateTopPadding())
                    .padding(top = 70.dp, start = 40.dp, end = 40.dp),
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
                    text = stringResource(id = R.string.app_name),
                    color = Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                BTTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = stringResource(id = R.string.email)
                )
                Spacer(modifier = Modifier.height(12.dp))
                BTTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = stringResource(id = R.string.password),
                    isSecret = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                BTTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = stringResource(id = R.string.confirm_password),
                    isSecret = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (!confirmResult) {
                    Text(
                        text = stringResource(id = R.string.passwords_do_not_match),
                        color = Black,
                        fontSize = 10.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                BTButton(
                    text = stringResource(id = R.string.register).uppercase(),
                    onClick = {
                        if (password == confirmPassword) {
                            confirmResult = true
                            userViewModel.registerUser(email, password)
                        } else {
                            confirmResult = false
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = { navController.navigate(Route.LOGIN) },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = BackgroundWhite,
                        contentColor = Black
                    )
                ) {
                    Text(stringResource(id = R.string.already_have_account))
                }
            }
        }
    )
}
