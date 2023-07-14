package com.example.dataapiexercise.adapter

import androidx.navigation.NavController
import com.example.dataapiexercise.network.Song

class FavouriteAdapter(
    private val songList: List<Song>,
    private val navController: NavController,
    private val onDetailClick: (Song) -> Unit
) {
}