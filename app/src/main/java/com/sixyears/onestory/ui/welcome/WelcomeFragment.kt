package com.sixyears.onestory.ui.welcome

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sixyears.onestory.databinding.FragmentWelcomeBinding
import com.sixyears.onestory.util.PrefsHelper

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler(Looper.getMainLooper())
    private var charIndex = 0
    private var navigated = false

    private val fullText: String by lazy {
        listOf(
            getString(com.sixyears.onestory.R.string.welcome_line_1),
            "",
            getString(com.sixyears.onestory.R.string.welcome_line_2),
            "",
            getString(com.sixyears.onestory.R.string.welcome_line_3)
        ).joinToString("\n")
    }

    companion object {
        private const val TYPE_SPEED_MS = 35L
        private const val HOLD_AFTER_TYPING_MS = 2500L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingHearts.start()
        startTypewriter()
    }

    private fun startTypewriter() {
        charIndex = 0
        binding.tvWelcomeText.text = ""
        typeNextChar()
    }

    private fun typeNextChar() {
        if (_binding == null) return
        if (charIndex <= fullText.length) {
            binding.tvWelcomeText.text = fullText.substring(0, charIndex)
            charIndex++
            handler.postDelayed({ typeNextChar() }, TYPE_SPEED_MS)
        } else {
            // Finished typing — hold, then auto-navigate.
            handler.postDelayed({ navigateNext() }, HOLD_AFTER_TYPING_MS)
        }
    }

    private fun navigateNext() {
        if (navigated || _binding == null) return
        navigated = true

        val destination = if (PrefsHelper.wasPermissionScreenShown(requireContext())) {
            WelcomeFragmentDirections.actionWelcomeToMain()
        } else {
            WelcomeFragmentDirections.actionWelcomeToPermission()
        }
        findNavController().navigate(destination)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        binding.floatingHearts.stop()
        _binding = null
    }
}
