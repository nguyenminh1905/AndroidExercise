package com.example.dataapiexercise.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_songs")
data class FavouriteSong(
    @PrimaryKey(autoGenerate = true)
    //for auto generated id song
    val id: Int?,
    @ColumnInfo("name")
    val name: String
)