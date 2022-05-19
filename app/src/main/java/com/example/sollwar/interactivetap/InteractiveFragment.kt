package com.example.sollwar.interactivetap

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sollwar.interactivetap.databinding.FragmentInteractiveBinding
import kotlinx.coroutines.*


class InteractiveFragment : Fragment() {

    private val viewModel by viewModels<ViewModelInteractiveFragment>()

    private var _binding: FragmentInteractiveBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInteractiveBinding.inflate(inflater, container, false)

        val videos = Videos(requireActivity().packageName)

        binding.videoView.setVideoURI(videos.nextVideo())
        binding.videoView.start()

        binding.playButton.setOnClickListener {
            binding.videoView.setVideoURI(videos.nextVideo())
            binding.videoView.start()
        }

        lifecycleScope.launch {
            while (true) {
                delay(100L)
                val currentDuration = binding.videoView.duration
                val currentPosition = binding.videoView.currentPosition
                binding.progressBar.progress = (currentPosition * 100) / currentDuration
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(): InteractiveFragment {
            return InteractiveFragment()
        }
    }

}