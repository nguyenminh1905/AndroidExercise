package com.example.dataapiexercise.database

import kotlinx.coroutines.flow.Flow

class FavouriteSongRepository(private val favouriteSongDao: FavouriteSongDao) {
    val allFavouriteSongs: Flow<List<FavouriteSong>> = favouriteSongDao.getAll()

    suspend fun insert(favouriteSong: FavouriteSong) {
        favouriteSongDao.insert(favouriteSong)
    }

    suspend fun delete(favouriteSong: FavouriteSong) {
        favouriteSongDao.delete(favouriteSong)
    }
}
