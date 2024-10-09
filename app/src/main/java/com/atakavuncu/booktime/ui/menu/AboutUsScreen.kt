package com.atakavuncu.booktime.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.data.model.user.Update
import com.atakavuncu.booktime.ui.components.BTAppBar
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.Blue

@Composable
fun AboutUsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            BTAppBar(
                title = stringResource(id = R.string.about_us),
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundWhite)
                .padding(top = paddingValues.calculateTopPadding())
                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.about_us_booktime),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Black
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = stringResource(id = R.string.about_us_contact_us),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp, color = Black),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )

            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.about_us_email)),
                onClick = { /* Handle email click */ },
                style = TextStyle(color = Blue, fontSize = 16.sp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.about_us_social_media),
                fontWeight = FontWeight.Bold,
                color = Black,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icons8_instagram),
                    tint = Black,
                    contentDescription = "Instagram",
                    modifier = Modifier.size(32.dp)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icons8_facebook),
                    contentDescription = "Facebook",
                    tint = Black,
                    modifier = Modifier.size(32.dp)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icons8_twitter),
                    contentDescription = "Twitter",
                    tint = Black,
                    modifier = Modifier.size(32.dp)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icons8_linkedin),
                    contentDescription = "LinkedIn",
                    tint = Black,
                    modifier = Modifier.size(32.dp)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icons8_medium),
                    contentDescription = "YouTube",
                    tint = Black,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.about_us_version_title),
                fontWeight = FontWeight.Bold,
                color = Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.about_us_current_version),
                    color = Black,
                    style = TextStyle(fontSize = 16.sp)
                )

                Text(
                    text = AnnotatedString(stringResource(id = R.string.about_us_version_number)),
                    style = TextStyle(color = Blue, fontSize = 16.sp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.about_us_last_update),
                    color = Black,
                    style = TextStyle(fontSize = 16.sp)
                )

                Text(
                    text = AnnotatedString(stringResource(id = R.string.about_us_last_update_date)),
                    style = TextStyle(color = Blue, fontSize = 16.sp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.about_us_copyright_title),
                fontWeight = FontWeight.Bold,
                color = Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.about_us_copyright_text),
                color = Black,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )

            val annotatedString = buildAnnotatedString {
                val text = stringResource(id = R.string.about_us_ownership)
                append(text)
                val start = text.indexOf("atakavuncu")
                val end = start + "atakavuncu".length

                addStyle(
                    style = SpanStyle(color = Black, fontSize = 14.sp),
                    start = 0,
                    end = text.length
                )
                addStyle(
                    style = SpanStyle( color = Blue ),
                    start = start,
                    end = end
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(annotatedString)
        }
    }
}
