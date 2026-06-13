package com.sixyears.onestory.model

/**
 * A single section in the "Our Story" timeline.
 * [imageResId] is nullable / may point to a non-existent resource — the adapter
 * must fall back to the placeholder drawable if loading fails.
 */
data class StorySection(
    val title: String,
    val description: String,
    val imageResId: Int? = null
)
