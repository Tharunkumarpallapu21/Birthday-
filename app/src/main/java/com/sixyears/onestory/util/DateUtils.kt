package com.sixyears.onestory.util

import com.sixyears.onestory.BuildConfig
import java.util.Calendar
import java.util.concurrent.TimeUnit

/**
 * Centralized date / countdown logic.
 * Birthday and relationship-start dates are configured via BuildConfig (app/build.gradle.kts).
 */
object DateUtils {

    /** Returns the next upcoming birthday as epoch millis. If today IS the birthday, returns today at 00:00. */
    fun nextBirthdayMillis(now: Calendar = Calendar.getInstance()): Long {
        val target = Calendar.getInstance()
        target.set(Calendar.MONTH, BuildConfig.BIRTHDAY_MONTH - 1)
        target.set(Calendar.DAY_OF_MONTH, BuildConfig.BIRTHDAY_DAY)
        target.set(Calendar.HOUR_OF_DAY, 0)
        target.set(Calendar.MINUTE, 0)
        target.set(Calendar.SECOND, 0)
        target.set(Calendar.MILLISECOND, 0)

        if (target.timeInMillis < startOfDay(now).timeInMillis) {
            target.add(Calendar.YEAR, 1)
        }
        return target.timeInMillis
    }

    private fun startOfDay(cal: Calendar): Calendar {
        val c = cal.clone() as Calendar
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        return c
    }

    /** True if today is the birthday. */
    fun isTodayBirthday(now: Calendar = Calendar.getInstance()): Boolean {
        return now.get(Calendar.MONTH) == BuildConfig.BIRTHDAY_MONTH - 1 &&
            now.get(Calendar.DAY_OF_MONTH) == BuildConfig.BIRTHDAY_DAY
    }

    /** Number of full days remaining until next birthday (0 if today is birthday). */
    fun daysUntilBirthday(now: Calendar = Calendar.getInstance()): Long {
        if (isTodayBirthday(now)) return 0
        val diff = nextBirthdayMillis(now) - startOfDay(now).timeInMillis
        return TimeUnit.MILLISECONDS.toDays(diff)
    }

    data class Countdown(val days: Long, val hours: Long, val minutes: Long, val seconds: Long)

    /** Live countdown (days/hours/min/sec) until the next birthday from current instant. */
    fun countdownToBirthday(now: Long = System.currentTimeMillis()): Countdown {
        val target = nextBirthdayMillis()
        var diff = target - now
        if (diff < 0) diff = 0

        val days = TimeUnit.MILLISECONDS.toDays(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60

        return Countdown(days, hours, minutes, seconds)
    }

    /**
     * "Love Growing" percentage: progress through the current year of the relationship,
     * i.e. how far we are between the last anniversary and the next one. 0-100.
     */
    fun loveGrowingPercent(now: Calendar = Calendar.getInstance()): Int {
        val anniversary = Calendar.getInstance()
        anniversary.set(Calendar.MONTH, BuildConfig.RELATIONSHIP_START_MONTH - 1)
        anniversary.set(Calendar.DAY_OF_MONTH, BuildConfig.RELATIONSHIP_START_DAY)
        anniversary.set(Calendar.HOUR_OF_DAY, 0)
        anniversary.set(Calendar.MINUTE, 0)
        anniversary.set(Calendar.SECOND, 0)
        anniversary.set(Calendar.MILLISECOND, 0)

        val lastAnniversary = anniversary.clone() as Calendar
        if (lastAnniversary.timeInMillis > now.timeInMillis) {
            lastAnniversary.add(Calendar.YEAR, -1)
        }

        val nextAnniversary = lastAnniversary.clone() as Calendar
        nextAnniversary.add(Calendar.YEAR, 1)

        val totalSpan = nextAnniversary.timeInMillis - lastAnniversary.timeInMillis
        val elapsed = now.timeInMillis - lastAnniversary.timeInMillis

        if (totalSpan <= 0) return 0
        val percent = ((elapsed.toDouble() / totalSpan.toDouble()) * 100).toInt()
        return percent.coerceIn(0, 100)
    }

    /** Number of full years since relationship start. */
    fun yearsTogether(now: Calendar = Calendar.getInstance()): Int {
        val start = Calendar.getInstance()
        start.set(BuildConfig.RELATIONSHIP_START_YEAR, BuildConfig.RELATIONSHIP_START_MONTH - 1, BuildConfig.RELATIONSHIP_START_DAY)

        var years = now.get(Calendar.YEAR) - start.get(Calendar.YEAR)
        if (now.get(Calendar.MONTH) < start.get(Calendar.MONTH) ||
            (now.get(Calendar.MONTH) == start.get(Calendar.MONTH) && now.get(Calendar.DAY_OF_MONTH) < start.get(Calendar.DAY_OF_MONTH))
        ) {
            years--
        }
        return years
    }
}
