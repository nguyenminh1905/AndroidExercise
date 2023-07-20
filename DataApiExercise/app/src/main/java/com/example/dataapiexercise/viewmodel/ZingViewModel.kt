package com.example.dataapiexercise.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dataapiexercise.network.Song
import com.example.dataapiexercise.network.ZingApiService
import kotlinx.coroutines.launch
import java.text.Normalizer

class ZingViewModel : ViewModel() {

    /**
     * Using livedata to auto update the UI
     */

    private val _songs = MutableLiveData<List<Song>>()
    val selectedSong = MutableLiveData<Song>()
    val isSearchOngoing: MutableLiveData<Boolean> = MutableLiveData(false)

    val songs:
            LiveData<List<Song>>
        get() = _songs
    val filteredSongs: MutableLiveData<List<Song>> = MutableLiveData()

    fun filter(query: String) {
        // Normalize the query by removing diacritical marks
        val normalizedQuery = Normalizer.normalize(query, Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")

        // Filter the songs that contain the query
        val filteredList = songs.value?.filter { song ->
            // Normalize the song name before comparing
            val normalizedSongName = Normalizer.normalize(song.name, Normalizer.Form.NFD)
                .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")

            normalizedSongName.contains(normalizedQuery, ignoreCase = true)
        } ?: emptyList()

        // Sort the list based on how closely the song name matches the query
        val sortedList = filteredList.sortedWith(compareBy { song ->
            var matchScore = 0
            // Normalize the song name before comparing
            val normalizedSongName = Normalizer.normalize(song.name, Normalizer.Form.NFD)
                .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
            // Score +1 if the song starts with the query
            if (normalizedSongName.startsWith(normalizedQuery, ignoreCase = true)) matchScore += 1
            // Score +1 if the song is exactly the query
            if (normalizedSongName.equals(normalizedQuery, ignoreCase = true)) matchScore += 1
            // Return the inverse of matchScore because lower scores should come first
            -matchScore
        })

        // Update the live data
        filteredSongs.value = sortedList
    }




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