package com.example.dataapiexercise.screens

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dataapiexercise.R
import com.example.dataapiexercise.viewmodel.ZingViewModel
import com.example.dataapiexercise.adapter.ZingMusicAdapter
import com.example.dataapiexercise.database.FavouriteApplication
import com.example.dataapiexercise.databinding.FragmentZingMusicBinding
import com.example.dataapiexercise.network.Song
import com.example.dataapiexercise.viewmodel.FavouriteSongViewModel
import com.example.dataapiexercise.viewmodel.FavouriteSongViewModelFactory


class ZingMusicFragment : Fragment() {

    private var _binding: FragmentZingMusicBinding? = null
    private val binding get() = _binding!!

    //delegate activityViewModels for sharing data between fragment
    private val zingViewModel: ZingViewModel by activityViewModels()
    private val favouriteSongViewModel: FavouriteSongViewModel by activityViewModels {
        FavouriteSongViewModelFactory(
            (activity?.application as FavouriteApplication).database.favouriteSongDao()
        )
    }
    private var recycleState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentZingMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        //observing songs change through viewmodel
        zingViewModel.songs.observe(viewLifecycleOwner) { songs ->
            val adapter =
                ZingMusicAdapter(songs, navController, ::onDetailClick, favouriteSongViewModel)
            // Update adapter when song list changes
            binding.recycleView.adapter = adapter
        }
        //restoring scroll state onPause
        binding.recycleView.layoutManager?.onRestoreInstanceState(recycleState)
    }

    /**
     * passing data of the selected song to the details
     */
    private fun onDetailClick(song: Song) {
        zingViewModel.selectedSong.value = song
    }

    override fun onPause() {
        super.onPause()
        recycleState = binding.recycleView.layoutManager?.onSaveInstanceState() // save state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
