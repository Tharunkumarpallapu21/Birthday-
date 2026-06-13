package com.sixyears.onestory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sixyears.onestory.R
import com.sixyears.onestory.databinding.ItemGalleryFullscreenBinding
import com.sixyears.onestory.model.GalleryPhoto

class FullscreenPagerAdapter(private val photos: List<GalleryPhoto>) :
    RecyclerView.Adapter<FullscreenPagerAdapter.PageViewHolder>() {

    inner class PageViewHolder(val binding: ItemGalleryFullscreenBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PageViewHolder {
        val binding = ItemGalleryFullscreenBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val photo = photos[position]
        try {
            holder.binding.ivFullscreen.setImageResource(photo.resId)
        } catch (_: Exception) {
            holder.binding.ivFullscreen.setImageResource(R.drawable.ic_placeholder_image)
        }
        holder.binding.ivFullscreen.resetZoom()
    }

    override fun getItemCount(): Int = photos.size
}
