package com.example.dataapiexercise

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dataapiexercise.network.Song
import com.example.dataapiexercise.network.ZingApiService
import kotlinx.coroutines.launch

/**
 * viewmodel to passing song data between fragment
 */
class ZingViewModel : ViewModel() {

    /**
     * Using livedata to auto update the UI
     */
    private val _songs = MutableLiveData<List<Song>>()
    val selectedSong = MutableLiveData<Song>()

    val songs: LiveData<List<Song>>
        get() = _songs

    init {
        fetchSongs()
    }

    /**
     * Fetching list of song inside view model coroutine
     */
    private fun fetchSongs() {
        viewModelScope.launch {
            try {
                val fetchedSongs = ZingApiService.create().getZingChart().data.song
                _songs.value = fetchedSongs
            } catch (e: Exception) {

                Log.e("ZingMusicFragment", "Failed to fetch songs", e)
            }
        }
    }
}