package com.example.dataapiexercise.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.dataapiexercise.R
import com.example.dataapiexercise.viewmodel.ZingViewModel
import com.example.dataapiexercise.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

            //formatting duration
            val minutes = song.duration / 60
            val seconds = song.duration % 60
            binding.duration.text = "Duration: ${String.format("%d:%02d", minutes, seconds)}"

            //Checking if the song in an album or not
            binding.albumName.text = "Album: ${song.album?.name ?: "N/A"}"

            //loading image
            binding.thumbnail.load(song.thumbnail) {
                placeholder(R.drawable.ic_zing)
                error(R.drawable.ic_broken_image)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}