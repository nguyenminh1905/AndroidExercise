package com.example.dataapiexercise.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_songs")
data class FavouriteSong(
    @PrimaryKey
    val id: Int = 0,

    val name: String,

    val isFavourite: Boolean = false
)
