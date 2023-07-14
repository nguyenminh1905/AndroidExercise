package com.example.dataapiexercise.adapter

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dataapiexercise.R
import com.example.dataapiexercise.database.FavouriteSong
import com.example.dataapiexercise.databinding.ListSongItemBinding
import com.example.dataapiexercise.network.Song
import com.example.dataapiexercise.viewmodel.FavouriteSongViewModel

class ZingMusicAdapter(private val songList: List<Song>,
                       private val navController: NavController,
                       private val onDetailClick: (Song) -> Unit,
                       private val viewModel: FavouriteSongViewModel
) :
    RecyclerView.Adapter<ZingMusicAdapter.ZingMusicHolder>() {

    inner class ZingMusicHolder(private val binding: ListSongItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentSong: Song? = null

        init {
            binding.overflowMenu.setOnClickListener { v ->
                val popup = PopupMenu(v.context, v)
                val inflater: MenuInflater = popup.menuInflater
                inflater.inflate(R.menu.three_dots_menu, popup.menu)

                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.favourite -> {
                            Toast.makeText(v.context, "Add to favourite!", Toast.LENGTH_SHORT)
                                .show()
                            currentSong?.let {
                                // Insert into database
                                viewModel.addFavourite(it.name)
                            }
                            true
                        }
                        R.id.detail -> {
                            currentSong?.let {
                                onDetailClick(it)
                            }
                            navController.navigate(R.id.action_zingMusicFragment_to_detailsFragment)
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }

            /**
             *  Passing the data with onDetailClick
             *  Sets the selectedSong value in the ViewModel to the song that was clicked on.
             */

            binding.card.setOnClickListener {
                currentSong?.let {
                    onDetailClick(it)
                }
                navController.navigate(R.id.action_zingMusicFragment_to_detailsFragment)
            }
        }

        fun bind(song: Song) {
            currentSong = song
            binding.position.text = song.position.toString()
            binding.songName.text = song.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZingMusicHolder {
        val binding =
            ListSongItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZingMusicHolder(binding)
    }

    override fun onBindViewHolder(holder: ZingMusicHolder, position: Int) {
        holder.bind(songList[position])
    }

    override fun getItemCount(): Int {
        return songList.size
    }
}
