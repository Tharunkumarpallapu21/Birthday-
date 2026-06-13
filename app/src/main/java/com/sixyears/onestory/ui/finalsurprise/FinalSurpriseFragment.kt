package com.sixyears.onestory.ui.finalsurprise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sixyears.onestory.databinding.FragmentFinalSurpriseBinding
import androidx.fragment.app.Fragment

class FinalSurpriseFragment : Fragment() {

    private var _binding: FragmentFinalSurpriseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinalSurpriseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingHearts.start()

        // Fade in the whole content block
        binding.layoutContent.alpha = 0f
        binding.layoutContent.animate()
            .alpha(1f)
            .setDuration(1200)
            .start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.floatingHearts.stop()
        _binding = null
    }
}
