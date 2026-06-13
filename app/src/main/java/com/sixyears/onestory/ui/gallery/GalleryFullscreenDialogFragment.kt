package com.sixyears.onestory.ui.gallery

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.sixyears.onestory.R
import com.sixyears.onestory.adapter.FullscreenPagerAdapter
import com.sixyears.onestory.databinding.DialogGalleryFullscreenBinding
import com.sixyears.onestory.model.GalleryPhoto

class GalleryFullscreenDialogFragment : DialogFragment() {

    private var photos: List<GalleryPhoto> = emptyList()
    private var startPosition: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogGalleryFullscreenBinding.inflate(layoutInflater)

        photos = arguments?.getIntArray(ARG_RES_IDS)?.map { GalleryPhoto(it) } ?: emptyList()
        startPosition = arguments?.getInt(ARG_START_POSITION, 0) ?: 0

        binding.pagerFullscreen.adapter = FullscreenPagerAdapter(photos)
        binding.pagerFullscreen.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.pagerFullscreen.setCurrentItem(startPosition, false)

        val dialog = Dialog(requireContext(), R.style.Theme_SixYearsOneStory)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.black)
        return dialog
    }

    companion object {
        private const val ARG_RES_IDS = "res_ids"
        private const val ARG_START_POSITION = "start_position"

        fun newInstance(resIds: IntArray, startPosition: Int): GalleryFullscreenDialogFragment {
            return GalleryFullscreenDialogFragment().apply {
                arguments = Bundle().apply {
                    putIntArray(ARG_RES_IDS, resIds)
                    putInt(ARG_START_POSITION, startPosition)
                }
            }
        }
    }
}
