package com.example.dataapiexercise.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteSongDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(song: FavouriteSong)

    @Delete
    suspend fun delete(song: FavouriteSong)

    @Query("SELECT * FROM favourite_songs")
    fun getAll(): Flow<List<FavouriteSong>>


    @Query("SELECT * FROM favourite_songs WHERE id = :songId LIMIT 1")
    suspend fun getSong(songId: Int): FavouriteSong?
}