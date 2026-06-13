package com.sixyears.onestory.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sixyears.onestory.R
import com.sixyears.onestory.databinding.FragmentMainCountdownBinding
import com.sixyears.onestory.viewmodel.CountdownViewModel

class MainCountdownFragment : Fragment() {

    private var _binding: FragmentMainCountdownBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CountdownViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainCountdownBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingHearts.start()
        animateCards()
        observeViewModel()
        setupClickListeners()
    }

    private fun animateCards() {
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up_fade_in)
        val cards = listOf(
            binding.cardOurStory,
            binding.cardReasons,
            binding.cardLoveLetter,
            binding.cardGallery
        )
        cards.forEachIndexed { index, card ->
            card.startAnimation(anim)
            card.alpha = 1f
            card.animate()
                .setStartDelay(index * 100L)
        }
    }

    private fun observeViewModel() {
        viewModel.countdown.observe(viewLifecycleOwner) { countdown ->
            binding.tvDays.text = countdown.days.toString().padStart(2, '0')
            binding.tvHours.text = countdown.hours.toString().padStart(2, '0')
            binding.tvMinutes.text = countdown.minutes.toString().padStart(2, '0')
            binding.tvSeconds.text = countdown.seconds.toString().padStart(2, '0')
        }

        viewModel.lovePercent.observe(viewLifecycleOwner) { percent ->
            binding.liquidHeart.setPercent(percent)
        }

        viewModel.isBirthdayToday.observe(viewLifecycleOwner) { isToday ->
            if (isToday) {
                // Auto-navigate to birthday celebration if it's the special day.
                try {
                    findNavController().navigate(
                        MainCountdownFragmentDirections.actionMainToBirthday()
                    )
                } catch (_: Exception) {
                    // Navigation might already be in progress / destination changed.
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.cardOurStory.setOnClickListener {
            findNavController().navigate(MainCountdownFragmentDirections.actionMainToStory())
        }
        binding.cardReasons.setOnClickListener {
            findNavController().navigate(MainCountdownFragmentDirections.actionMainToReasons())
        }
        binding.cardLoveLetter.setOnClickListener {
            findNavController().navigate(MainCountdownFragmentDirections.actionMainToLetter())
        }
        binding.cardGallery.setOnClickListener {
            findNavController().navigate(MainCountdownFragmentDirections.actionMainToGallery())
        }
        binding.btnBirthdayPreview.setOnClickListener {
            findNavController().navigate(MainCountdownFragmentDirections.actionMainToBirthday())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.floatingHearts.stop()
        _binding = null
    }
}
