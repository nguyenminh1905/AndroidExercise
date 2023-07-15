package com.example.dataapiexercise.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
    private val viewModel: FavouriteSongViewModel by activityViewModels {
        FavouriteSongViewModelFactory(
            (activity?.application as FavouriteApplication).database.favouriteSongDao()
        )
    }  // Initialize your ViewModel

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
            findNavController(), // NavController
            :: onDetailClick,        // onDetailClick
            :: onDeleteClick         // onDeleteClick
        )
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.adapter = adapter

        // Observe favourite songs from the database and update the adapter when the data changes
        viewModel.allFavouriteSongs.observe(viewLifecycleOwner) { songs ->
            songs?.let { adapter.submitList(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onDetailClick(favouriteSong: FavouriteSong) {
        val zingViewModel = ViewModelProvider(requireActivity())[ZingViewModel::class.java]
        val matchingSong = zingViewModel.songs.value?.find { it.name == favouriteSong.name }
        matchingSong?.let { zingViewModel.selectedSong.value = it }
    }

    private fun onDeleteClick(favouriteSong: FavouriteSong){
        viewModel.delete(favouriteSong)
    }
}
