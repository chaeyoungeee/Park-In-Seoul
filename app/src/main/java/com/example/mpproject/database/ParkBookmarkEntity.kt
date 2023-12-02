package com.example.mpproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_bookmark")
data class ParkBookmarkEntity(
    @PrimaryKey
    var name: String = "",
    var isSelected: Boolean = false
)