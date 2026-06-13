package com.sixyears.onestory.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sixyears.onestory.adapter.GalleryAdapter
import com.sixyears.onestory.data.GalleryRepository
import com.sixyears.onestory.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photos = try {
            GalleryRepository.getPhotos()
        } catch (_: Exception) {
            emptyList()
        }

        if (photos.isEmpty()) {
            binding.rvGallery.visibility = View.GONE
            binding.layoutEmpty.visibility = View.VISIBLE
        } else {
            binding.layoutEmpty.visibility = View.GONE
            binding.rvGallery.visibility = View.VISIBLE
            binding.rvGallery.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.rvGallery.adapter = GalleryAdapter(photos) { position ->
                val resIds = photos.map { it.resId }.toIntArray()
                GalleryFullscreenDialogFragment.newInstance(resIds, position)
                    .show(parentFragmentManager, "gallery_fullscreen")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
