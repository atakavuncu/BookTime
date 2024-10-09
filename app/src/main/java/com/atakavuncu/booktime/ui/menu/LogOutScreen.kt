package com.atakavuncu.booktime.ui.menu

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.data.model.user.Update
import com.atakavuncu.booktime.ui.auth.UserViewModel
import com.atakavuncu.booktime.ui.components.BTAppBar
import com.atakavuncu.booktime.ui.navigation.Route
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.Blue
import com.atakavuncu.booktime.ui.theme.Red
import com.atakavuncu.booktime.utils.SessionManager
import kotlin.system.exitProcess

@Composable
fun LogOutScreen(
    navController: NavHostController,
    sessionManager: SessionManager,
    viewModel: UserViewModel = hiltViewModel()
) {
    var isLoggedOut by remember { mutableStateOf(false) }

    if (isLoggedOut){
        RestartApp()
    }

    Scaffold(
        topBar = {
            BTAppBar(
                title = stringResource(id = R.string.logout),
                titleColor = Red,
                navController = navController,
                backButtonEnable = true
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundWhite)
                .padding(top = paddingValues.calculateTopPadding(), start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(stringResource(id = R.string.logout_confirm), color = Black, textAlign = TextAlign.Center)
            Button(
                onClick = {
                    sessionManager.setIsLoggedIn(false)
                    viewModel.logOut()
                    isLoggedOut = true
                },
                modifier = Modifier
                    .padding(top = 30.dp)
                    .width(150.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Red),
                shape = RoundedCornerShape(16)
            ) {
                Text(stringResource(id = R.string.logout).uppercase(), color = Color.White)
            }
        }
    }
}

@Composable
fun RestartApp() {
    val context = LocalContext.current
    val packageManager: PackageManager = context.packageManager
    val intent: Intent = packageManager.getLaunchIntentForPackage(context.packageName)!!
    val componentName: ComponentName = intent.component!!
    val restartIntent: Intent = Intent.makeRestartActivityTask(componentName)
    context.startActivity(restartIntent)
    Runtime.getRuntime().exit(0)
}