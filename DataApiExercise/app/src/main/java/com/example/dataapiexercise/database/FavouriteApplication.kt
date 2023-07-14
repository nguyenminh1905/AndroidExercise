package com.example.dataapiexercise.database

import android.app.Application

class FavouriteApplication : Application() {
    val database: FavouriteSongDatabase by lazy { FavouriteSongDatabase.getDatabase(this) }
}