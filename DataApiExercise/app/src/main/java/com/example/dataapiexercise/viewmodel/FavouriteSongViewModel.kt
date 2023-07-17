package com.example.dataapiexercise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dataapiexercise.database.FavouriteSong
import com.example.dataapiexercise.database.FavouriteSongDao
import kotlinx.coroutines.launch

class FavouriteSongViewModel(private val favouriteSongDao: FavouriteSongDao) : ViewModel() {

    val allFavouriteSongs: LiveData<List<FavouriteSong>> = favouriteSongDao.getAll().asLiveData()

    private fun insert(favouriteSong: FavouriteSong) = viewModelScope.launch {
        favouriteSongDao.insert(favouriteSong)
    }

    fun delete(favouriteSong: FavouriteSong) = viewModelScope.launch {
        favouriteSongDao.delete(favouriteSong)
    }

    fun addFavourite(songName: String) {
        val favouriteSong = FavouriteSong(name = songName)
        insert(favouriteSong)
    }
}

class FavouriteSongViewModelFactory(private val favouriteSongDao: FavouriteSongDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteSongViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouriteSongViewModel(favouriteSongDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
