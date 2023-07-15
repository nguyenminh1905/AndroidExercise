package com.example.dataapiexercise.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class ZingResponse(
    val data: ZingData
)

data class ZingData(
    val song: List<Song>
)

data class Song(
    val position: Int,
    val name: String,
    val artists_names: String,
    val duration: Int,
    val thumbnail: String?,
    val album: Album?
)

data class Album(
    val name: String
)