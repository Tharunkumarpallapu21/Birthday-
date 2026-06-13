package com.sixyears.onestory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sixyears.onestory.R
import com.sixyears.onestory.databinding.ItemGalleryThumbBinding
import com.sixyears.onestory.model.GalleryPhoto

class GalleryAdapter(
    private val photos: List<GalleryPhoto>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<GalleryAdapter.ThumbViewHolder>() {

    inner class ThumbViewHolder(val binding: ItemGalleryThumbBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ThumbViewHolder {
        val binding = ItemGalleryThumbBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ThumbViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThumbViewHolder, position: Int) {
        val photo = photos[position]
        try {
            holder.binding.ivThumb.setImageResource(photo.resId)
        } catch (_: Exception) {
            holder.binding.ivThumb.setImageResource(R.drawable.ic_placeholder_image)
        }
        holder.itemView.setOnClickListener { onClick(position) }
    }

    override fun getItemCount(): Int = photos.size
}
