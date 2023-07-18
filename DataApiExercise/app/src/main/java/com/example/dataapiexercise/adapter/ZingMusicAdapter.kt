package com.example.dataapiexercise.adapter

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dataapiexercise.R
import com.example.dataapiexercise.databinding.ListSongItemBinding
import com.example.dataapiexercise.network.Song
import com.example.dataapiexercise.viewmodel.FavouriteSongViewModel

class ZingMusicAdapter(
    private val songList: List<Song>,
    private val navController: NavController,
    private val onDetailClick: (Song) -> Unit,
    private val viewModel: FavouriteSongViewModel
) :
    RecyclerView.Adapter<ZingMusicAdapter.ZingMusicHolder>() {
    
    inner class ZingMusicHolder(private val binding: ListSongItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentSong: Song? = null

        init {
            binding.favourite.setOnClickListener { v ->
                currentSong?.let {
                    // Toggle favourite state
                    viewModel.toggleFavourite(it.position, it.name)
                    // Change heart icon according to the new state
                    if (viewModel.isFavourite(it.position)) {
                        binding.favourite.setImageResource(R.drawable.ic_fill_fav)
                        Toast.makeText(v.context, "Remove from favourite", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.favourite.setImageResource(R.drawable.ic_empty_fav)
                        Toast.makeText(v.context, "Added to favourite!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
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
            // Change heart icon according to favourite state
            if (viewModel.isFavourite(song.position)) {
                binding.favourite.setImageResource(R.drawable.ic_fill_fav)
            } else {
                binding.favourite.setImageResource(R.drawable.ic_empty_fav)
            }
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ZingMusicAdapter.ZingMusicHolder {
        val binding =
            ListSongItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZingMusicHolder(binding)
    }

    override fun onBindViewHolder(holder: ZingMusicAdapter.ZingMusicHolder, position: Int) {
        holder.bind(songList[position])
    }

    override fun getItemCount(): Int {
        return songList.size
    }
}
