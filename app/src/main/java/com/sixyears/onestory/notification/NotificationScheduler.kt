package com.sixyears.onestory.notification

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.sixyears.onestory.util.DateUtils
import java.util.Calendar
import java.util.concurrent.TimeUnit

/**
 * Schedules all notifications:
 * - Daily "Good Morning" at 7:00 AM (recurring)
 * - Daily "Countdown" at 8:00 PM (recurring)
 * - 4 special "Birthday Day" notifications, scheduled for the exact upcoming birthday
 *
 * All scheduling is offline (WorkManager only, no server / push needed).
 * Periodic work continues until the birthday "arrives" naturally; after the
 * birthday passes, [scheduleAll] (called again on next app open) will simply
 * compute the next year's birthday for the day-specific work.
 */
object NotificationScheduler {

    private const val GOOD_MORNING_HOUR = 7
    private const val COUNTDOWN_HOUR = 20 // 8 PM

    fun scheduleAll(context: Context) {
        scheduleGoodMorning(context)
        scheduleCountdown(context)
        scheduleBirthdayDayNotifications(context)
    }

    private fun scheduleGoodMorning(context: Context) {
        val initialDelay = delayUntil(GOOD_MORNING_HOUR, 0)

        val request: PeriodicWorkRequest = PeriodicWorkRequestBuilder<GoodMorningWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            GoodMorningWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    private fun scheduleCountdown(context: Context) {
        val initialDelay = delayUntil(COUNTDOWN_HOUR, 0)

        val request: PeriodicWorkRequest = PeriodicWorkRequestBuilder<CountdownWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            CountdownWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    private fun scheduleBirthdayDayNotifications(context: Context) {
        val birthdayMillis = DateUtils.nextBirthdayMillis()

        scheduleOneTimeAt(
            context,
            birthdayMillis + offsetMillis(0, 0, 0),   // 12:00 AM
            BirthdayDayWorker.SLOT_MIDNIGHT
        )
        scheduleOneTimeAt(
            context,
            birthdayMillis + offsetMillis(7, 0, 0),   // 7:00 AM
            BirthdayDayWorker.SLOT_MORNING
        )
        scheduleOneTimeAt(
            context,
            birthdayMillis + offsetMillis(12, 0, 0),  // 12:00 PM
            BirthdayDayWorker.SLOT_NOON
        )
        scheduleOneTimeAt(
            context,
            birthdayMillis + offsetMillis(18, 0, 0),  // 6:00 PM
            BirthdayDayWorker.SLOT_EVENING
        )
    }

    private fun scheduleOneTimeAt(context: Context, targetMillis: Long, slot: String) {
        val now = System.currentTimeMillis()
        var delay = targetMillis - now
        if (delay < 0) {
            // Already passed for "today" — schedule for next year's birthday instead.
            delay += TimeUnit.DAYS.toMillis(365)
        }

        val data = workDataOf(BirthdayDayWorker.KEY_SLOT to slot)

        val request: OneTimeWorkRequest = OneTimeWorkRequestBuilder<BirthdayDayWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            BirthdayDayWorker.workName(slot),
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    /** Milliseconds from "now" until the next occurrence of [hour]:[minute]. */
    private fun delayUntil(hour: Int, minute: Int): Long {
        val now = Calendar.getInstance()
        val target = now.clone() as Calendar
        target.set(Calendar.HOUR_OF_DAY, hour)
        target.set(Calendar.MINUTE, minute)
        target.set(Calendar.SECOND, 0)
        target.set(Calendar.MILLISECOND, 0)

        if (target.timeInMillis <= now.timeInMillis) {
            target.add(Calendar.DAY_OF_YEAR, 1)
        }
        return target.timeInMillis - now.timeInMillis
    }

    private fun offsetMillis(hour: Int, minute: Int, second: Int): Long {
        return TimeUnit.HOURS.toMillis(hour.toLong()) +
            TimeUnit.MINUTES.toMillis(minute.toLong()) +
            TimeUnit.SECONDS.toMillis(second.toLong())
    }
}
