package com.sixyears.onestory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.sixyears.onestory.R
import com.sixyears.onestory.databinding.ItemReasonBinding

class ReasonsAdapter(private val reasons: List<String>) :
    RecyclerView.Adapter<ReasonsAdapter.ReasonViewHolder>() {

    inner class ReasonViewHolder(val binding: ItemReasonBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ReasonViewHolder {
        val binding = ItemReasonBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ReasonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReasonViewHolder, position: Int) {
        holder.binding.tvReasonNumber.text = (position + 1).toString()
        holder.binding.tvReasonText.text = reasons[position]

        val anim = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_up_fade_in)
        holder.itemView.startAnimation(anim)
    }

    override fun getItemCount(): Int = reasons.size
}
