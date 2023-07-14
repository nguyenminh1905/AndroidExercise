package com.example.dataapiexercise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dataapiexercise.database.FavouriteSong
import com.example.dataapiexercise.database.FavouriteSongRepository
import kotlinx.coroutines.launch

class FavouriteSongViewModel(private val repository: FavouriteSongRepository) : ViewModel() {

    val allFavouriteSongs: LiveData<List<FavouriteSong>> = repository.allFavouriteSongs.asLiveData()
    val selectedSong = MutableLiveData<FavouriteSong>()


    private fun insert(favouriteSong: FavouriteSong) = viewModelScope.launch {
        repository.insert(favouriteSong)
    }

    fun delete(favouriteSong: FavouriteSong) = viewModelScope.launch {
        repository.delete(favouriteSong)
    }

    fun addFavourite(songName: String) {
        val favouriteSong = FavouriteSong(id = null, name = songName)
        insert(favouriteSong)
    }
}

class FavouriteSongViewModelFactory(private val repository: FavouriteSongRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteSongViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouriteSongViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
