package com.sixyears.onestory.ui.reasons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sixyears.onestory.adapter.ReasonsAdapter
import com.sixyears.onestory.data.ReasonsRepository
import com.sixyears.onestory.databinding.FragmentReasonsBinding

class ReasonsFragment : Fragment() {

    private var _binding: FragmentReasonsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReasonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvReasons.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReasons.adapter = ReasonsAdapter(ReasonsRepository.reasons)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
