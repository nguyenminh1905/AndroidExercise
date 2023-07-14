package com.example.dataapiexercise.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavouriteSong::class], version = 1, exportSchema = false)
abstract class FavouriteSongDatabase : RoomDatabase() {

    abstract fun favouriteSongDao(): FavouriteSongDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteSongDatabase? = null

        fun getDatabase(context: Context): FavouriteSongDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteSongDatabase::class.java,
                    "item_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}