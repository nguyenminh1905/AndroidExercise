package com.example.dataapiexercise.screens

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
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

    private lateinit var navController: NavController
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)  // tell the fragment to call onCreateOptionsMenu
    }

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
        navController = findNavController()

        //observing songs change through viewmodel
        zingViewModel.isSearchOngoing.observe(viewLifecycleOwner) { isSearch ->
            if (isSearch) {
                // If a search is ongoing, observe filteredSongs
                zingViewModel.filteredSongs.observe(viewLifecycleOwner) { songs ->
                    val adapter = ZingMusicAdapter(songs, ::onDetailClick, favouriteSongViewModel)
                    binding.recycleView.adapter = adapter
                }
            } else {
                // If there's no search, observe songs
                zingViewModel.songs.observe(viewLifecycleOwner) { songs ->
                    val adapter = ZingMusicAdapter(songs, ::onDetailClick, favouriteSongViewModel)
                    binding.recycleView.adapter = adapter
                }
            }
        }
        //restoring scroll state onPause
        binding.recycleView.layoutManager?.onRestoreInstanceState(recycleState)
    }

    /**
     * passing data of the selected song to the details
     */
    private fun onDetailClick(song: Song) {
        zingViewModel.selectedSong.value = song
        navController.navigate(R.id.action_zingMusicFragment_to_detailsFragment)

    }

    override fun onPause() {
        super.onPause()
        recycleState = binding.recycleView.layoutManager?.onSaveInstanceState() // save state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                zingViewModel.isSearchOngoing.value = !newText.isNullOrEmpty()
                zingViewModel.filter(newText ?: "")
                return true
            }
        })
    }
}