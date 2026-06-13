package com.sixyears.onestory.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sixyears.onestory.data.MessageRepository
import com.sixyears.onestory.util.DateUtils

class CountdownWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val daysLeft = DateUtils.daysUntilBirthday()

        // If it's already the birthday (or birthday just passed today), the
        // birthday-day workers handle messaging instead — skip the evening countdown.
        if (DateUtils.isTodayBirthday()) {
            return Result.success()
        }

        val message = MessageRepository.countdownMessage(daysLeft)
        NotificationHelper.showNotification(
            applicationContext,
            NotificationHelper.NOTIF_ID_COUNTDOWN,
            "❤️ Countdown to Your Day",
            message
        )
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "countdown_work"
    }
}
