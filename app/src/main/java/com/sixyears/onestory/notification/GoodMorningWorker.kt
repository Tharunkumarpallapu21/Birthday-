package com.sixyears.onestory.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sixyears.onestory.data.MessageRepository
import com.sixyears.onestory.util.PrefsHelper

class GoodMorningWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val lastIndex = PrefsHelper.getLastGoodMorningIndex(applicationContext)
        val (message, newIndex) = MessageRepository.pickGoodMorningMessage(lastIndex)
        PrefsHelper.setLastGoodMorningIndex(applicationContext, newIndex)

        // Message format: "🌞 Title\nBody" — split on first newline for title/body.
        val parts = message.split("\n", limit = 2)
        val title = parts.getOrElse(0) { "Good Morning Love ❤️" }
        val body = parts.getOrElse(1) { message }

        NotificationHelper.showNotification(
            applicationContext,
            NotificationHelper.NOTIF_ID_GOOD_MORNING,
            title,
            body
        )
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "good_morning_work"
    }
}
