package com.atakavuncu.booktime.data.model.user

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "favorites",
    primaryKeys = ["userId", "bookId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Favorites(
    val userId: Int,
    val bookId: String
)
