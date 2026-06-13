package com.sixyears.onestory.ui.letter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sixyears.onestory.databinding.FragmentLoveLetterBinding

class LoveLetterFragment : Fragment() {

    private var _binding: FragmentLoveLetterBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler(Looper.getMainLooper())
    private var charIndex = 0

    companion object {
        private const val TYPE_SPEED_MS = 18L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoveLetterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutEnvelope.setOnClickListener {
            openEnvelope()
        }
    }

    private fun openEnvelope() {
        // Envelope opening animation: scale down + fade out
        binding.layoutEnvelope.animate()
            .scaleX(0.6f)
            .scaleY(0.6f)
            .alpha(0f)
            .setDuration(400)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.layoutEnvelope.visibility = View.GONE
                    showLetter()
                }
            })
            .start()
    }

    private fun showLetter() {
        // Paper unfolding animation: scale up from small + fade in
        binding.scrollLetter.visibility = View.VISIBLE
        binding.scrollLetter.scaleY = 0.1f
        binding.scrollLetter.alpha = 0f
        binding.scrollLetter.pivotY = 0f

        binding.scrollLetter.animate()
            .scaleY(1f)
            .alpha(1f)
            .setDuration(600)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    startTypewriter()
                }
            })
            .start()
    }

    private fun startTypewriter() {
        val fullText = getString(com.sixyears.onestory.R.string.letter_body)
        charIndex = 0
        binding.tvLetterBody.text = ""
        typeNextChar(fullText)
    }

    private fun typeNextChar(fullText: String) {
        if (_binding == null) return
        if (charIndex <= fullText.length) {
            binding.tvLetterBody.text = fullText.substring(0, charIndex)
            charIndex++
            handler.postDelayed({ typeNextChar(fullText) }, TYPE_SPEED_MS)
        } else {
            // Reveal signature with a gentle fade-in
            binding.tvLetterSignature.alpha = 0f
            binding.tvLetterSignature.visibility = View.VISIBLE
            binding.tvLetterSignature.animate()
                .alpha(1f)
                .setDuration(600)
                .start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        _binding = null
    }
}
