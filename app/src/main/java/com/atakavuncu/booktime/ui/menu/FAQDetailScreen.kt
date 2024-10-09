package com.atakavuncu.booktime.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.ui.components.BTAppBar
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.StickGrey
import com.atakavuncu.booktime.ui.theme.White

@Composable
fun FAQDetailScreen(
    title: String,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            BTAppBar(
                title = stringResource(id = R.string.faq),
                navController = navController,
                backButtonEnable = true
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            Divider(
                color = StickGrey,
                thickness = 0.5.dp
            )
            when(title) {
                stringResource(id = R.string.faq_account_and_profile) -> {
                    FAQItem(
                        question = stringResource(id = R.string.faq_account_and_profile_question_1),
                        answer = stringResource(id = R.string.faq_account_and_profile_answer_1)
                    )
                    FAQItem(
                        question = stringResource(id = R.string.faq_account_and_profile_question_2),
                        answer = stringResource(id = R.string.faq_account_and_profile_answer_2)
                    )
                    FAQItem(
                        question = stringResource(id = R.string.faq_account_and_profile_question_3),
                        answer = stringResource(id = R.string.faq_account_and_profile_answer_3)
                    )
                }
                stringResource(id = R.string.faq_books) -> {
                    FAQItem(
                        question = stringResource(id = R.string.faq_books_question_1),
                        answer = stringResource(id = R.string.faq_books_answer_1)
                    )
                }
                stringResource(id = R.string.faq_settings) -> {
                    FAQItem(
                        question = stringResource(id = R.string.faq_settings_question_1),
                        answer = stringResource(id = R.string.faq_settings_answer_1)
                    )
                    FAQItem(
                        question = stringResource(id = R.string.faq_settings_question_2),
                        answer = stringResource(id = R.string.faq_settings_answer_2)
                    )
                }
            }
        }
    }
}

@Composable
fun FAQItem(question: String, answer: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(16.dp)
    ) {
        Text(
            text = question,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Black
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = answer,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Black
            )
        )
    }
    Divider(
        color = StickGrey,
        thickness = 0.5.dp
    )
}
