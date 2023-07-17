package com.example.dataapiexercise.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dataapiexercise.R
import com.example.dataapiexercise.database.FavouriteSong
import com.example.dataapiexercise.databinding.ListSongItemBinding

class FavouriteSongAdapter(
    private val navController: NavController,
    private val onDetailClick: (FavouriteSong) -> Unit,
    private val onDeleteClick: (FavouriteSong) -> Unit
) : ListAdapter<FavouriteSong, FavouriteSongAdapter.FavouriteSongHolder>(FavouriteSongComparator()) {

    inner class FavouriteSongHolder(private val binding: ListSongItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentFavouriteSong: FavouriteSong? = null

        init {
            binding.overflowMenu.setOnClickListener { v ->
                val popup = PopupMenu(v.context, v)
                val inflater: MenuInflater = popup.menuInflater
                inflater.inflate(R.menu.favourite_three_dots_menu, popup.menu)

                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.delete -> {
                            currentFavouriteSong?.let {
                                onDeleteClick(it)
                            }
                            true
                        }

                        R.id.detail -> {
                            currentFavouriteSong?.let {
                                onDetailClick(it)
                            }
                            navController.navigate(R.id.action_favouriteFragment_to_detailsFragment)
                            true
                        }

                        else -> false
                    }
                }
                popup.show()
            }

            binding.card.setOnClickListener {
                currentFavouriteSong?.let {
                    onDetailClick(it)
                }
                navController.navigate(R.id.action_favouriteFragment_to_detailsFragment)
            }
        }

        fun bind(favouriteSong: FavouriteSong, position: Int) {
            currentFavouriteSong = favouriteSong
            binding.position.text = (position + 1).toString()
            binding.songName.text = favouriteSong.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteSongHolder {
        val binding =
            ListSongItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteSongHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteSongHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class FavouriteSongComparator : DiffUtil.ItemCallback<FavouriteSong>() {
        override fun areItemsTheSame(oldItem: FavouriteSong, newItem: FavouriteSong): Boolean {
            // Id is unique.
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: FavouriteSong, newItem: FavouriteSong): Boolean {
            // This can be enhanced based on specific fields when data class changes
            return oldItem == newItem
        }
    }
}

