package com.sixyears.onestory.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sixyears.onestory.data.MessageRepository
import com.sixyears.onestory.util.DateUtils

/**
 * Handles the four special birthday-day notifications (midnight, morning, noon, evening).
 * Input data key [KEY_SLOT] determines which message/id is used.
 * Each worker checks that "today" is actually the birthday before firing —
 * this guards against drift if the work was scheduled for a future date
 * that later gets recalculated.
 */
class BirthdayDayWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        if (!DateUtils.isTodayBirthday()) {
            return Result.success()
        }

        val slot = inputData.getString(KEY_SLOT) ?: return Result.failure()

        val (title, body, id) = when (slot) {
            SLOT_MIDNIGHT -> Triple(
                MessageRepository.BirthdayDay.MIDNIGHT,
                "Wishing you the happiest birthday, my love.",
                NotificationHelper.NOTIF_ID_BIRTHDAY_MIDNIGHT
            )
            SLOT_MORNING -> Triple(
                MessageRepository.BirthdayDay.MORNING,
                "Today is all about you. I hope it's perfect from start to finish.",
                NotificationHelper.NOTIF_ID_BIRTHDAY_MORNING
            )
            SLOT_NOON -> Triple(
                "🎉 Happy Birthday ❤️",
                MessageRepository.BirthdayDay.NOON,
                NotificationHelper.NOTIF_ID_BIRTHDAY_NOON
            )
            SLOT_EVENING -> Triple(
                "❤️ Surprise Waiting",
                MessageRepository.BirthdayDay.EVENING,
                NotificationHelper.NOTIF_ID_BIRTHDAY_EVENING
            )
            else -> return Result.failure()
        }

        NotificationHelper.showNotification(
            applicationContext,
            id,
            title,
            body,
            channelId = com.sixyears.onestory.SixYearsApp.CHANNEL_BIRTHDAY
        )
        return Result.success()
    }

    companion object {
        const val KEY_SLOT = "slot"
        const val SLOT_MIDNIGHT = "midnight"
        const val SLOT_MORNING = "morning"
        const val SLOT_NOON = "noon"
        const val SLOT_EVENING = "evening"

        fun workName(slot: String) = "birthday_day_work_$slot"
    }
}
