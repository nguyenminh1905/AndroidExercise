package com.example.dataapiexercise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.dataapiexercise.R
import com.example.dataapiexercise.databinding.ListSongItemBinding
import com.example.dataapiexercise.network.Song
import com.example.dataapiexercise.viewmodel.FavouriteSongViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ZingMusicAdapter(
    private val songList: List<Song>,
    private val onDetailClick: (Song) -> Unit,
    private val viewModel: FavouriteSongViewModel
) :
    RecyclerView.Adapter<ZingMusicAdapter.ZingMusicHolder>() {

    inner class ZingMusicHolder(private val binding: ListSongItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song, position: Int) {
            binding.position.text = song.position.toString()
            binding.songName.text = song.name
            binding.favourite.setOnClickListener { v ->
                song.let {
                    viewModel.toggleFavourite(it.position, it.name)
                    viewModel.viewModelScope.launch(Dispatchers.Main) {
                        delay(300)
                        notifyItemChanged(position)
                    }
                }
            }
            viewModel.isFavourite(song.position) {
                if (it) {
                    binding.favourite.setImageResource(R.drawable.ic_fill_fav)
                } else {
                    binding.favourite.setImageResource(R.drawable.ic_empty_fav)
                }
            }
            binding.card.setOnClickListener {
                onDetailClick(song)
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
        holder.bind(songList[position], position)
    }

    override fun getItemCount(): Int {
        return songList.size
    }
}