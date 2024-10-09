package com.atakavuncu.booktime.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.data.model.user.Update
import com.atakavuncu.booktime.ui.auth.UserViewModel
import com.atakavuncu.booktime.ui.components.BTAppBar
import com.atakavuncu.booktime.ui.components.BTTextField
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.Blue
import com.atakavuncu.booktime.ui.theme.Red
import com.atakavuncu.booktime.ui.theme.TextBlack
import com.atakavuncu.booktime.ui.theme.White
import com.atakavuncu.booktime.utils.SessionManager

@Composable
fun UpdateScreen(
    navController: NavHostController,
    whatToUpdate: Update,
    viewModel: UserViewModel = hiltViewModel()
) {
    var old by remember { mutableStateOf("") }
    var new by remember { mutableStateOf("") }
    var newRepeat by remember { mutableStateOf("") }
    var isConfirm by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            BTAppBar(
                title = when (whatToUpdate) {
                    Update.EMAIL -> {
                        stringResource(id = R.string.email_update)
                    }

                    Update.PASSWORD -> {
                        stringResource(id = R.string.password_update)
                    }
                },
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundWhite)
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BTTextField(
                value = old,
                onValueChange = { old = it },
                label = when (whatToUpdate) {
                    Update.EMAIL -> {
                        stringResource(id = R.string.old_email_address)
                    }

                    Update.PASSWORD -> {
                        stringResource(id = R.string.old_password)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            BTTextField(
                value = new,
                onValueChange = { new = it },
                label = when (whatToUpdate) {
                    Update.EMAIL -> {
                        stringResource(id = R.string.new_email_address)
                    }

                    Update.PASSWORD -> {
                        stringResource(id = R.string.new_password)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            BTTextField(
                value = newRepeat,
                onValueChange = { newRepeat = it },
                label = when (whatToUpdate) {
                    Update.EMAIL -> {
                        stringResource(id = R.string.new_email_address_confirm)
                    }

                    Update.PASSWORD -> {
                        stringResource(id = R.string.new_password_confirm)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (new == newRepeat) {
                        when (whatToUpdate) {
                            Update.EMAIL -> {
                                viewModel.updateEmail(new)
                            }

                            Update.PASSWORD -> {
                                viewModel.updatePassword(new)
                            }
                        }
                    } else {
                        isConfirm = false
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(150.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue),
                shape = RoundedCornerShape(16)
            ) {
                Text(stringResource(id = R.string.confirm), color = Color.White)
            }

            if (!isConfirm) {
                Text(text = stringResource(id = R.string.wrong_info), color = Red)
            }
        }
    }
}
