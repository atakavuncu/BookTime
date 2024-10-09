package com.atakavuncu.booktime.ui.components

import android.icu.text.ListFormatter.Width
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.data.model.user.Update
import com.atakavuncu.booktime.ui.theme.Blue
import com.atakavuncu.booktime.ui.theme.White

@Composable
fun BTButton(
    text: String,
    backgroundColor: Color = Blue,
    textColor: Color = White,
    modifier: Modifier? = null,
    onClick: () -> Unit
) {
    val buttonModifier = modifier ?: Modifier
        .padding(top = 16.dp)
        .width(150.dp)
        .height(48.dp)
    Button(
        onClick = { onClick() },
        modifier = buttonModifier,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16)
    ) {
        Text(text = text, color = textColor)
    }
}