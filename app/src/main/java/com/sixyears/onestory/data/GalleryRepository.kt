package com.sixyears.onestory.data

import com.sixyears.onestory.model.GalleryPhoto

/**
 * Returns the list of photos to show in the Memory Gallery.
 * By default this is empty (no images bundled with the project) so the
 * gallery screen shows the "Memories Coming Soon" empty state.
 *
 * To add real photos later:
 * 1. Drop image files into res/drawable (e.g. memory_01.jpg, memory_02.jpg)
 * 2. Add entries here, e.g. GalleryPhoto(R.drawable.memory_01, "Our first trip")
 */
object GalleryRepository {

    fun getPhotos(): List<GalleryPhoto> = emptyList()
}
