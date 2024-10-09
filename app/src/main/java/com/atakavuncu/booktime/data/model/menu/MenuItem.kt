package com.atakavuncu.booktime.data.model.menu


data class MenuItem(
    val title: String,
    val onClick: () -> Unit
)
