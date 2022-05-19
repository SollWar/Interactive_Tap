package com.example.sollwar.interactivetap

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
    private var deltaX = 3f
    private var deltaY = 3f

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
                delay(16L)

                val currentDuration = binding.videoView.duration
                val currentPosition = binding.videoView.currentPosition
                binding.progressBar.progress = (currentPosition * 100) / currentDuration

                binding.playButton.x += deltaX
                binding.playButton.y += deltaY

                if (binding.videoView.height > 0 && binding.playButton.y >= binding.videoView.height - 120) {
                    deltaY = -deltaY
                } else if (binding.videoView.height > 0 && binding.playButton.y <= - 38) {
                    deltaY = -deltaY
                }
                if (binding.videoView.width > 0 && binding.playButton.x >= binding.videoView.width - 100) {
                    deltaX = -deltaX
                } else if (binding.videoView.width > 0 && binding.playButton.x <= - 38) {
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