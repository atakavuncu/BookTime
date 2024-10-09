package com.atakavuncu.booktime.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.White

@Composable
fun BTTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isSecret: Boolean = false,
    modifier: Modifier? = null,
    onDone: (() -> Unit)? = null
) {
    val textFieldModifier = modifier ?: Modifier.fillMaxWidth()
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(label, fontSize = 12.sp) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = White,
            focusedTextColor = Black,
            focusedLabelColor = Black,
            focusedPlaceholderColor = Black,
            focusedIndicatorColor = Color.Transparent,
            cursorColor = Black,
            unfocusedContainerColor = White,
            unfocusedTextColor = Black,
            unfocusedLabelColor = Black,
            unfocusedPlaceholderColor = Black,
            unfocusedIndicatorColor = Color.Transparent
        ),
        visualTransformation = if (isSecret) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = textFieldModifier
            .onKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Enter) {
                    if (onDone != null) {
                        onDone()
                    }
                    true
                } else {
                    false
                }
            }
    )
}