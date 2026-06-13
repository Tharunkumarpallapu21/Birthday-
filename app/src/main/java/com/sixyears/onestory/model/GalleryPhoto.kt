package com.sixyears.onestory.model

/**
 * Represents a gallery photo. [resId] may be null if no drawable was added —
 * the gallery must show the empty-state UI in that case without crashing.
 */
data class GalleryPhoto(
    val resId: Int,
    val caption: String = ""
)
