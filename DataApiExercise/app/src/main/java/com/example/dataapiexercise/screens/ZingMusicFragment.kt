package com.example.dataapiexercise.screens

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dataapiexercise.viewmodel.ZingViewModel
import com.example.dataapiexercise.adapter.ZingMusicAdapter
import com.example.dataapiexercise.database.FavouriteSongDatabase
import com.example.dataapiexercise.database.FavouriteSongRepository
import com.example.dataapiexercise.databinding.FragmentZingMusicBinding
import com.example.dataapiexercise.network.Song
import com.example.dataapiexercise.viewmodel.FavouriteSongViewModel
import com.example.dataapiexercise.viewmodel.FavouriteSongViewModelFactory


class ZingMusicFragment : Fragment() {

    private var _binding: FragmentZingMusicBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ZingViewModel
    private lateinit var favouriteSongViewModel: FavouriteSongViewModel
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

        //setting up the viewmodel
        viewModel = ViewModelProvider(requireActivity())[ZingViewModel::class.java]
        val songDao = FavouriteSongDatabase.getDatabase(requireContext()).favouriteSongDao()
        val repository = FavouriteSongRepository(songDao)
        val favouriteSongViewModelFactory = FavouriteSongViewModelFactory(repository)
        favouriteSongViewModel = ViewModelProvider(
            this,
            favouriteSongViewModelFactory
        )[FavouriteSongViewModel::class.java]


        viewModel.songs.observe(viewLifecycleOwner) { songs ->
            // Update adapter when song list changes
            val navController = findNavController()
            val adapter =
                ZingMusicAdapter(songs, navController, ::onDetailClick, favouriteSongViewModel)
            binding.recycleView.adapter = adapter
            binding.recycleView.layoutManager = LinearLayoutManager(requireContext())

            // Restore state after onPause
            binding.recycleView.layoutManager?.onRestoreInstanceState(recycleState)

            // Restore recycleview state after screen rotation
            //if (savedInstanceState != null) {
            //   val savedRecyclerLayoutState =
            //      savedInstanceState.getParcelable<Parcelable>("recycler_state")
            //  binding.recycleView.layoutManager?.onRestoreInstanceState(savedRecyclerLayoutState)
            // }
        }
    }

    /**
     * passing data of the selected song to the details
     */
    private fun onDetailClick(song: Song) {
        viewModel.selectedSong.value = song
    }

    /**
    override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    binding?.recycleView?.layoutManager?.onSaveInstanceState()?.let { state ->
    outState.putParcelable("recycler_state", state)
    }
    }
     */

    override fun onPause() {
        super.onPause()
        recycleState = binding.recycleView.layoutManager?.onSaveInstanceState() // save state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


