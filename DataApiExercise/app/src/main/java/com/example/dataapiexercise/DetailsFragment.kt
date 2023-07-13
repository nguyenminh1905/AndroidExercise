package com.example.dataapiexercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.dataapiexercise.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[ZingViewModel::class.java]

        viewModel.selectedSong.observe(viewLifecycleOwner) { song ->
            // Display the song details
            binding.name.text = "Song name : ${song.name}"
            binding.artist.text = "Artist: ${song.artists_names}"
            binding.duration.text = "Duration: ${song.duration.toString()}"
            binding.albumName.text = "Album: ${song.album?.name ?: "N/A"}"
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}