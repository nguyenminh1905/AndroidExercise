package com.example.dataapiexercise

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dataapiexercise.databinding.FragmentZingMusicBinding
import com.example.dataapiexercise.network.Song


class ZingMusicFragment : Fragment() {

    private var _binding: FragmentZingMusicBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ZingViewModel

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


        viewModel.songs.observe(viewLifecycleOwner) { songs ->
            // Update adapter when song list changes
            val navController = findNavController()
            val adapter = ZingMusicAdapter(songs, navController, ::onDetailClick)
            binding.recycleView.adapter = adapter
            binding.recycleView.layoutManager = LinearLayoutManager(requireContext())

            // Restore state after rotation
            if (savedInstanceState != null) {
                val savedRecyclerLayoutState = savedInstanceState.getParcelable<Parcelable>("recycler_state")
                binding.recycleView.layoutManager?.onRestoreInstanceState(savedRecyclerLayoutState)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * passing data of the selected song to the details
     */
    private fun onDetailClick(song: Song) {
        viewModel.selectedSong.value = song
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("recycler_state", binding.recycleView.layoutManager?.onSaveInstanceState())
    }
}


