package com.atakavuncu.booktime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atakavuncu.booktime.ui.theme.DarkGrey
import com.atakavuncu.booktime.ui.theme.Grey

@Composable
fun BTLabel(
    text: String,
    width: Int? = null,
    height: Int? = null,
    backgroundColor: Color = Grey,
    textColor: Color = DarkGrey,
    textSize: Int = 12,
    textWeight: FontWeight = FontWeight.Normal,
    shape: Shape? = null
) {
    val boxShape: Shape = shape ?: ButtonDefaults.shape

    val boxModifier = Modifier
        .then(if (height != null) Modifier.height(height.dp) else Modifier)
        .then(if (width != null) Modifier.width(width.dp) else Modifier)
        .clip(boxShape)
        .background(color = backgroundColor)
        .padding(horizontal = 16.dp, vertical = 4.dp)

    Box( modifier = boxModifier ) {
        Text(text = text, fontSize = textSize.sp, fontWeight = textWeight, color = textColor)
    }
}