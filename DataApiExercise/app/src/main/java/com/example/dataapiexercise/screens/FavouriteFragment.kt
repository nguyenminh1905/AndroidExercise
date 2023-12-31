package com.example.dataapiexercise.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dataapiexercise.R
import com.example.dataapiexercise.adapter.FavouriteSongAdapter
import com.example.dataapiexercise.database.FavouriteApplication
import com.example.dataapiexercise.database.FavouriteSong
import com.example.dataapiexercise.databinding.FragmentFavouriteBinding
import com.example.dataapiexercise.viewmodel.FavouriteSongViewModel
import com.example.dataapiexercise.viewmodel.FavouriteSongViewModelFactory
import com.example.dataapiexercise.viewmodel.ZingViewModel

class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private val zingViewModel: ZingViewModel by activityViewModels()
    private val favouriteSongViewModel: FavouriteSongViewModel by activityViewModels {
        FavouriteSongViewModelFactory(
            (activity?.application as FavouriteApplication).database.favouriteSongDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavouriteSongAdapter(
            ::onDetailClick,
            ::onDeleteClick
        )
        binding.recycleView.adapter = adapter
        adapter.notifyDataSetChanged()

        // Observe favourite songs from the database and update the adapter when the data changes
        favouriteSongViewModel.allFavouriteSongs.observe(viewLifecycleOwner) { songs ->
            songs?.let { adapter.submitList(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onDetailClick(favouriteSong: FavouriteSong) {
        val matchingSong = zingViewModel.songs.value?.find { it.name == favouriteSong.name }
        matchingSong?.let { zingViewModel.selectedSong.value = it }
        val navController = findNavController()
        navController.navigate(R.id.action_favouriteFragment_to_detailsFragment)
    }

    private fun onDeleteClick(favouriteSong: FavouriteSong) {
        favouriteSongViewModel.delete(favouriteSong)
    }
}
