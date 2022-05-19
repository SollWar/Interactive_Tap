package com.example.sollwar.interactivetap

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sollwar.interactivetap.databinding.FragmentInteractiveBinding
import kotlinx.coroutines.*


class InteractiveFragment : Fragment() {

    private var _binding: FragmentInteractiveBinding? = null
    private val binding get() = _binding!!

    // Скорость перемещения кнопки по осям
    private var deltaX = 6f
    private var deltaY = 6f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInteractiveBinding.inflate(inflater, container, false)

        // Временный репозиторий с видео
        val videosRepository = VideosRepository(requireActivity().packageName)

        binding.videoView.setVideoURI(videosRepository.nextVideo())
        binding.videoView.start()

        binding.playButton.setOnClickListener {
            binding.videoView.setVideoURI(videosRepository.nextVideo())
            binding.videoView.start()
        }

        // Вечный цикл для анимации
        lifecycleScope.launch {
            while (true) {
                // ~ 60 fps
                delay(15L)

                val currentDuration = binding.videoView.duration
                val currentPosition = binding.videoView.currentPosition
                val progress = (currentPosition * 100) / currentDuration

                binding.progressBarLeft.progress = progress
                binding.progressBarRight.progress = progress
                binding.progressTextLeft.text = (100 - progress).toString()
                binding.progressTextRight.text = (100 - progress).toString()

                when (progress) {
                    in 0..49 -> {
                        binding.progressTextRight.setTextColor(Color.WHITE)
                        binding.progressTextLeft.setTextColor(Color.WHITE)
                    }
                    in 50..74 -> {
                        binding.progressTextRight.setTextColor(Color.GREEN)
                        binding.progressTextLeft.setTextColor(Color.GREEN)
                    }
                    in 75..100 -> {
                        binding.progressTextRight.setTextColor(Color.RED)
                        binding.progressTextLeft.setTextColor(Color.RED)
                    }
                }

                if (binding.videoView.isPlaying) {
                    binding.playButton.x += deltaX
                    binding.playButton.y += deltaY
                } else {
                    binding.progressBarLeft.progress = 100
                    binding.progressBarRight.progress = 100
                    binding.progressTextLeft.text = "0"
                    binding.progressTextRight.text = "0"
                }



                if (binding.playButton.y > container!!.height - binding.playButton.height / 1.5) {
                    deltaY = -deltaY
                } else if (binding.playButton.y < - binding.playButton.height / 3) {
                    deltaY = -deltaY
                }

                if (binding.playButton.x > container.width - binding.playButton.width / 1.5) {
                    deltaX = -deltaX
                } else if (binding.playButton.x < - binding.playButton.width / 3) {
                    deltaX = -deltaX
                }
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