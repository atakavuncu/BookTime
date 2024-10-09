package com.atakavuncu.booktime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.atakavuncu.booktime.ui.theme.BookTimeTheme
import com.atakavuncu.booktime.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookTimeTheme {
                val navController = rememberNavController()
                val sessionManager = SessionManager(this)
                BookTimeApp(sessionManager = sessionManager, navController = navController)
            }
        }
    }
}