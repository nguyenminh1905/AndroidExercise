package com.example.dataapiexercise.adapter

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dataapiexercise.R
import com.example.dataapiexercise.database.FavouriteSong
import com.example.dataapiexercise.databinding.ListFavouriteItemBinding

class FavouriteSongAdapter(
    private val onDetailClick: (FavouriteSong) -> Unit,
    private val onDeleteClick: (FavouriteSong) -> Unit
) : ListAdapter<FavouriteSong, FavouriteSongAdapter.FavouriteSongHolder>(FavouriteSongComparator()) {

    inner class FavouriteSongHolder(private val binding: ListFavouriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouriteSong: FavouriteSong) {
            binding.songName.text = favouriteSong.name
            //take in lambda can do as v -> or simply just pass it
            binding.overflowMenu.setOnClickListener {v ->
                val popup = PopupMenu(v.context, v)
                val inflater: MenuInflater = popup.menuInflater
                inflater.inflate(R.menu.favourite_three_dots_menu, popup.menu)

                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.delete -> {
                            onDeleteClick(favouriteSong)
                            true
                        }

                        R.id.detail -> {
                            onDetailClick(favouriteSong)
                            true
                        }

                        else -> false
                    }
                }
                popup.show()
            }

            binding.card.setOnClickListener {
                onDetailClick(favouriteSong)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteSongHolder {
        val binding =
            ListFavouriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteSongHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteSongHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FavouriteSongComparator : DiffUtil.ItemCallback<FavouriteSong>() {
        override fun areItemsTheSame(oldItem: FavouriteSong, newItem: FavouriteSong): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: FavouriteSong, newItem: FavouriteSong): Boolean {
            return oldItem == newItem
        }
    }
}

