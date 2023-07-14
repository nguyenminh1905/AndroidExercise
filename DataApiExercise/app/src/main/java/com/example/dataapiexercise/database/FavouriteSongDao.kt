package com.example.dataapiexercise.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.dataapiexercise.network.Song

@Dao
interface FavouriteSongDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(song: FavouriteSong)

    @Delete
    suspend fun delete(song: FavouriteSong)
}