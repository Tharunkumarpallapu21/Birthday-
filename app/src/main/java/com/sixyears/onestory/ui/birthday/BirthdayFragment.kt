package com.sixyears.onestory.ui.birthday

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sixyears.onestory.R
import com.sixyears.onestory.databinding.FragmentBirthdayBinding

class BirthdayFragment : Fragment() {

    private var _binding: FragmentBirthdayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBirthdayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingHearts.start()
        binding.confettiView.start()

        // Cake bounce animation
        val cakeAnim = ObjectAnimator.ofPropertyValuesHolder(
            binding.ivCake,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.1f, 1f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.1f, 1f)
        ).apply {
            duration = 1200
            repeatCount = ObjectAnimator.INFINITE
        }
        cakeAnim.start()

        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up_fade_in)
        binding.tvBirthdayTitle.startAnimation(fadeIn)
        binding.tvBirthdayMessage.startAnimation(fadeIn)

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(BirthdayFragmentDirections.actionBirthdayToFinal())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.floatingHearts.stop()
        binding.confettiView.stop()
        _binding = null
    }
}
