package com.atakavuncu.booktime.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atakavuncu.booktime.ui.theme.Black

@Composable
fun BTIconText (
    iconVector: ImageVector,
    text: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = iconVector,
            contentDescription = null,
            tint = Black,
            modifier = Modifier.padding(end = 16.dp).size(20.dp)
        )
        Text(
            text = text,
            color = Black,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}