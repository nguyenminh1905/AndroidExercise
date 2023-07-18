package com.example.dataapiexercise.viewmodel

import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dataapiexercise.database.PhoneSong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicViewModel : ViewModel() {

    private val _songs = MutableLiveData<List<PhoneSong>>()
    val songs: LiveData<List<PhoneSong>> = _songs

    fun loadSongs(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val newSongs = fetchSongsFromDevice(context)
            _songs.postValue(newSongs)
        }
    }

    private fun fetchSongsFromDevice(context: Context): List<PhoneSong> {
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(uri, null, null, null, null)

        val songs = mutableListOf<PhoneSong>()
        cursor?.let { c ->
            val nameColumnIndex = c.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistColumnIndex = c.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val dataColumnIndex = c.getColumnIndex(MediaStore.Audio.Media.DATA)

            while (c.moveToNext()) {
                val name = c.getString(nameColumnIndex)
                val artist = c.getString(artistColumnIndex)
                val data = c.getString(dataColumnIndex)

                songs.add(PhoneSong(name, artist, data))
            }
        }
        cursor?.close()
        return songs
    }
}
