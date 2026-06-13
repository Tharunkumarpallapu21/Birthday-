package com.sixyears.onestory.data

import com.sixyears.onestory.R
import com.sixyears.onestory.model.StorySection

object StoryRepository {

    fun getSections(): List<StorySection> = listOf(
        StorySection(
            title = "How We Met ❤️",
            description = "It started with a moment neither of us expected — a simple hello that quietly became the beginning of everything. Looking back, I think some part of me knew right away that you were going to be important.",
            imageResId = null
        ),
        StorySection(
            title = "First Memories ❤️",
            description = "The early days — nervous laughs, long conversations that went on till late at night, and the excitement of getting to know someone who felt instantly familiar. Every little memory from that time still makes me smile.",
            imageResId = null
        ),
        StorySection(
            title = "Difficult Times ❤️",
            description = "Not every chapter was easy. We faced distance, misunderstandings, and moments that tested us. But through it all, we chose each other, again and again. Those hard days taught us how strong our love really is.",
            imageResId = null
        ),
        StorySection(
            title = "Stronger Together ❤️",
            description = "Every challenge we faced made us closer instead of pulling us apart. We learned how to support each other, how to listen better, and how to turn our 'we' into something unshakable.",
            imageResId = null
        ),
        StorySection(
            title = "Growing Together ❤️",
            description = "We've grown — individually and together. New dreams, new plans, new versions of ourselves... but always with each other by our side. Watching you grow has been one of my favorite things to witness.",
            imageResId = null
        ),
        StorySection(
            title = "Six Years One Story ❤️",
            description = "Six years, countless memories, and one beautiful story that's still being written. Here's to everything we've shared so far, and to everything still waiting for us. I love you more than these words could ever say.",
            imageResId = null
        )
    )
}
