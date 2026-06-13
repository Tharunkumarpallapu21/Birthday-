package com.sixyears.onestory.ui.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sixyears.onestory.adapter.StoryAdapter
import com.sixyears.onestory.data.StoryRepository
import com.sixyears.onestory.databinding.FragmentOurStoryBinding

class OurStoryFragment : Fragment() {

    private var _binding: FragmentOurStoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOurStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvStory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStory.adapter = StoryAdapter(StoryRepository.getSections())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
