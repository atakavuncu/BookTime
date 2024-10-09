package com.atakavuncu.booktime.ui.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.ui.components.BTAppBar
import com.atakavuncu.booktime.ui.navigation.Route
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.Red
import com.atakavuncu.booktime.ui.theme.StickGrey
import com.atakavuncu.booktime.ui.theme.White
import com.atakavuncu.booktime.utils.SessionManager

@Composable
fun LanguageScreen(
    navController: NavHostController,
    sessionManager: SessionManager
) {
    var selectedLanguage by remember { mutableStateOf("Türkçe") }
    val context = LocalContext.current
    val turkish = stringResource(id = R.string.turkish)
    val english = stringResource(id = R.string.english)

    LaunchedEffect(Unit) {
        val currentLanguage = sessionManager.getUserLanguage(context)
        selectedLanguage = if (currentLanguage == "tr") turkish else english
    }

    Scaffold(
        topBar = {
            BTAppBar(
                title = stringResource(id = R.string.language),
                navController = navController,
                backButtonEnable = true
            )
        },
        containerColor = BackgroundWhite
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            Divider(color = StickGrey, thickness = 0.5.dp)
            LanguageSelectionItem(
                language = turkish,
                isSelected = selectedLanguage == turkish,
                onClick = {
                    selectedLanguage = turkish
                    sessionManager.updateLanguage(context, "tr")
                },
                flagResId = R.drawable.tr_flag
            )
            Divider(color = StickGrey, thickness = 0.5.dp)
            LanguageSelectionItem(
                language = english,
                isSelected = selectedLanguage == english,
                onClick = {
                    selectedLanguage = english
                    sessionManager.updateLanguage(context, "en")
                },
                flagResId = R.drawable.uk_flag
            )
        }
    }
}

@Composable
fun LanguageSelectionItem(
    language: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    flagResId: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(color = White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = Black, unselectedColor = Black)
        )
        Image(
            painter = painterResource(id = flagResId),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(start = 8.dp)
        )
        Text(
            text = language,
            modifier = Modifier.padding(start = 8.dp),
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Black)
        )
    }
    Divider(color = StickGrey, thickness = 0.5.dp)
}

