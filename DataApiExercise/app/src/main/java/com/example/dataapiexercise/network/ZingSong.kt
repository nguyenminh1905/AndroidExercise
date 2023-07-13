package com.example.dataapiexercise.network

data class ZingResponse(
    val data: ZingData
)

data class ZingData(
    val song: List<Song>
)

data class Song(
    val id: String,
    val name: String,
    val artists_names: String,
    val duration: Int,
    val album: Album?
)

data class Album(
    val name: String
)