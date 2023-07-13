package com.example.dataapiexercise

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dataapiexercise.databinding.FragmentZingMusicBinding
import com.example.dataapiexercise.network.ZingApiService
import kotlinx.coroutines.launch

class ZingMusicFragment : Fragment() {

    private var _binding: FragmentZingMusicBinding? = null
    private val binding get() = _binding!!

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

        /**
        Placeholder

        val songList = listOf(
        Song("1", "Song 1", "Artist 1", 2, Album("PlaceHolder")),
        Song("2", "Song 2", "Artist 2", 2, Album("PlaceHolder")),
        Song("3", "Song 3", "Artist 3",2, Album("PlaceHolder"))
        )

        val navController = findNavController()
        val adapter = ZingMusicAdapter(songList, navController)
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
         **/
        val apiService = ZingApiService.create()
        lifecycleScope.launch {
            try {
                val zingChartResponse = apiService.getZingChart()
                val songList = zingChartResponse.data.song

                // Set up the RecyclerView with the adapter
                val navController = findNavController()
                val adapter = ZingMusicAdapter(songList, navController)
                binding.recycleView.adapter = adapter
                binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
            } catch (e: Exception) {
                Log.e("ZingMusicFragment", "Failed to fetch songs", e)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

