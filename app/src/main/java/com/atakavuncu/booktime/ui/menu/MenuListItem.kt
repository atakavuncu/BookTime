package com.atakavuncu.booktime.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atakavuncu.booktime.data.model.menu.MenuItem
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.StickGrey
import com.atakavuncu.booktime.ui.theme.White

@Composable
fun MenuListItem(item: MenuItem, textColor: Color = Black) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White)
            .clickable { item.onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.title,
            color = textColor,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = textColor,
            modifier = Modifier.size(24.dp)
        )
    }
    Divider(
        color = StickGrey,
        thickness = 0.5.dp
    )
}