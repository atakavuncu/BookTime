package com.atakavuncu.booktime.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.Grey
import com.atakavuncu.booktime.ui.theme.White

@Composable
fun PageNumberPicker(
    range: IntRange,
    selectedOption: String,
    selectedPageNumber: Int,
    onValueSelected: (Int) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(selectedPageNumber.toFloat()) }
    val sliderWidth = 300
    var isSliderEnabled = true

    if (selectedOption == "Okudum") {
        sliderPosition = range.last.toFloat()
        isSliderEnabled = false
    } else if (selectedOption == "Okumadım") {
        sliderPosition = range.first.toFloat()
        isSliderEnabled = false
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            IconButton(
                onClick = {
                    if (isSliderEnabled && sliderPosition > range.first) {
                        sliderPosition -= 1
                        onValueSelected(sliderPosition.toInt())
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = White,
                    contentColor = Black
                ),
                enabled = isSliderEnabled,
                modifier = Modifier
                    .width(50.dp)
                    .height(30.dp)
                    .border(1.dp, Black, shape = RoundedCornerShape(30))
                    .padding(0.dp)
            ) {
                Text(text = "−")
            }
            IconButton(
                onClick = {
                    if (isSliderEnabled && sliderPosition < range.last) {
                        sliderPosition += 1
                        onValueSelected(sliderPosition.toInt())
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = White,
                    contentColor = Black
                ),
                enabled = isSliderEnabled,
                modifier = Modifier
                    .width(50.dp)
                    .height(30.dp)
                    .border(1.dp, Black, shape = RoundedCornerShape(30))
                    .padding(0.dp)
            ) {
                Text(text = "+")
            }
        }
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onValueSelected(it.toInt())
            },
            valueRange = range.first.toFloat()..range.last.toFloat(),
            modifier = Modifier
                .width(sliderWidth.dp)
                .align(Alignment.CenterHorizontally),
            colors = SliderDefaults.colors(thumbColor = Black, activeTrackColor = Black),
            enabled = isSliderEnabled
        )
        val text = sliderPosition.toInt().toString()
        val thumbPadding = (sliderWidth * sliderPosition) / range.last
        val textPadding = (thumbPadding - (text.length - 1) * 8).dp + 16.dp
        Text(
            text = text,
            fontSize = 16.sp,
            color = if(isSliderEnabled) Black else Grey,
            modifier = Modifier
                .padding(start = textPadding)
        )
    }
}
