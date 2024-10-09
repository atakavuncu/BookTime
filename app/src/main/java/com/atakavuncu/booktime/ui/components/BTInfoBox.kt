package com.atakavuncu.booktime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.BorderBlack
import com.atakavuncu.booktime.ui.theme.FieldWhite

@Composable
fun BTInfoBox(
    title: String,
    contentText: String,
    textColor: Color = Black,
    backgroundColor: Color = FieldWhite,
    borderColor: Color = BorderBlack,
    width: Int? = null,
    height: Int? = null
) {
    val boxModifier = Modifier
        .then(if (height != null) Modifier.height(height.dp) else Modifier)
        .then(if (width != null) Modifier.width(width.dp) else Modifier)
        .clip(RoundedCornerShape(16))
        .border(0.2.dp, borderColor, RoundedCornerShape(16))
        .background(color = backgroundColor)
        .padding(5.dp)

    Box(
        modifier = boxModifier,
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = textColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp
            )
            Text(
                text = contentText,
                color = textColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
        }
    }
}