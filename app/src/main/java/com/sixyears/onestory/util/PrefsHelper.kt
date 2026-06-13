package com.sixyears.onestory.util

import android.content.Context

object PrefsHelper {
    private const val PREFS_NAME = "six_years_prefs"
    private const val KEY_LAST_GOOD_MORNING_INDEX = "last_good_morning_index"
    private const val KEY_NOTIFICATIONS_GRANTED = "notifications_granted"
    private const val KEY_PERMISSION_SCREEN_SHOWN = "permission_screen_shown"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getLastGoodMorningIndex(context: Context): Int =
        prefs(context).getInt(KEY_LAST_GOOD_MORNING_INDEX, -1)

    fun setLastGoodMorningIndex(context: Context, index: Int) {
        prefs(context).edit().putInt(KEY_LAST_GOOD_MORNING_INDEX, index).apply()
    }

    fun setNotificationsGranted(context: Context, granted: Boolean) {
        prefs(context).edit().putBoolean(KEY_NOTIFICATIONS_GRANTED, granted).apply()
    }

    fun areNotificationsGranted(context: Context): Boolean =
        prefs(context).getBoolean(KEY_NOTIFICATIONS_GRANTED, false)

    fun setPermissionScreenShown(context: Context, shown: Boolean) {
        prefs(context).edit().putBoolean(KEY_PERMISSION_SCREEN_SHOWN, shown).apply()
    }

    fun wasPermissionScreenShown(context: Context): Boolean =
        prefs(context).getBoolean(KEY_PERMISSION_SCREEN_SHOWN, false)
}
