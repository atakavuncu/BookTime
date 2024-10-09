package com.atakavuncu.booktime.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.atakavuncu.booktime.R

val Poppins = FontFamily(
    Font(R.font.poppins_thin, FontWeight.W100, FontStyle.Normal),
    Font(R.font.poppins_thin_italic, FontWeight.W100, FontStyle.Italic),

    Font(R.font.poppins_extra_light, FontWeight.W200, FontStyle.Normal),
    Font(R.font.poppins_extra_light_italic, FontWeight.W200, FontStyle.Italic),

    Font(R.font.poppins_light, FontWeight.W300, FontStyle.Normal),
    Font(R.font.poppins_light_italic, FontWeight.W300, FontStyle.Italic),

    Font(R.font.poppins_regular, FontWeight.W400, FontStyle.Normal),
    Font(R.font.poppins_italic, FontWeight.W400, FontStyle.Italic),

    Font(R.font.poppins_medium, FontWeight.W500, FontStyle.Normal),
    Font(R.font.poppins_medium_italic, FontWeight.W500, FontStyle.Italic),

    Font(R.font.poppins_semibold, FontWeight.W600, FontStyle.Normal),
    Font(R.font.poppins_semibold_italic, FontWeight.W600, FontStyle.Italic),

    Font(R.font.poppins_bold, FontWeight.W700, FontStyle.Normal),
    Font(R.font.poppins_bold_italic, FontWeight.W700, FontStyle.Italic),

    Font(R.font.poppins_extra_bold, FontWeight.W800, FontStyle.Normal),
    Font(R.font.poppins_extra_bold_italic, FontWeight.W800, FontStyle.Italic),

    Font(R.font.poppins_black, FontWeight.W900, FontStyle.Normal),
    Font(R.font.poppins_black_italic, FontWeight.W900, FontStyle.Italic),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)