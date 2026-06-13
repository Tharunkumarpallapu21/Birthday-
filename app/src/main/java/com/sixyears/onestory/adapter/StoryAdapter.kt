package com.sixyears.onestory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.sixyears.onestory.R
import com.sixyears.onestory.databinding.ItemStorySectionBinding
import com.sixyears.onestory.model.StorySection

class StoryAdapter(private val sections: List<StorySection>) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(val binding: ItemStorySectionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): StoryViewHolder {
        val binding = ItemStorySectionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val section = sections[position]
        holder.binding.tvStoryTitle.text = section.title
        holder.binding.tvStoryDescription.text = section.description

        // Safely set image, falling back to placeholder if resource is null or invalid.
        val resId = section.imageResId
        try {
            if (resId != null && resId != 0) {
                holder.binding.ivStoryImage.setImageResource(resId)
            } else {
                holder.binding.ivStoryImage.setImageResource(R.drawable.ic_placeholder_image)
                holder.binding.ivStoryImage.scaleType = android.widget.ImageView.ScaleType.CENTER
            }
        } catch (_: Exception) {
            holder.binding.ivStoryImage.setImageResource(R.drawable.ic_placeholder_image)
            holder.binding.ivStoryImage.scaleType = android.widget.ImageView.ScaleType.CENTER
        }

        // Entry animation
        val anim = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_up_fade_in)
        holder.itemView.startAnimation(anim)
    }

    override fun getItemCount(): Int = sections.size
}
