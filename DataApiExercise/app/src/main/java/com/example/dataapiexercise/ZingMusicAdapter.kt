package com.example.dataapiexercise

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dataapiexercise.databinding.ListSongItemBinding
import com.example.dataapiexercise.network.Song

class ZingMusicAdapter(private val songList: List<Song>, private val navController: NavController) :
    RecyclerView.Adapter<ZingMusicAdapter.ZingMusicHolder>() {

    inner class ZingMusicHolder(val binding: ListSongItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            //v is view being clicked
            binding.overflowMenu.setOnClickListener { v ->
                val popup = PopupMenu(v.context, v)
                val inflater: MenuInflater = popup.menuInflater
                inflater.inflate(R.menu.three_dots_menu, popup.menu)

                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.favourite -> {
                            Toast.makeText(v.context, "Add to favourite!", Toast.LENGTH_SHORT)
                                .show()
                            true
                        }

                        R.id.detail -> {
                            navController.navigate(R.id.action_zingMusicFragment_to_detailsFragment)
                            true
                        }

                        else -> false
                    }
                }
                popup.show()
            }

            // Set click listener for card view
            binding.card.setOnClickListener {
                navController.navigate(R.id.action_zingMusicFragment_to_detailsFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZingMusicHolder {
        val binding =
            ListSongItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZingMusicHolder(binding)
    }

    override fun onBindViewHolder(holder: ZingMusicHolder, position: Int) {
        val song = songList[position]
        holder.binding.id.text = (position + 1).toString()
        holder.binding.songName.text = song.name
    }

    override fun getItemCount(): Int {
        return songList.size
    }
}
