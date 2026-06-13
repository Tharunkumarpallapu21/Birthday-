package com.sixyears.onestory

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.sixyears.onestory.notification.NotificationScheduler

class SixYearsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
        NotificationScheduler.scheduleAll(this)
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)

            val generalChannel = NotificationChannel(
                CHANNEL_GENERAL,
                getString(R.string.notif_channel_general_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = getString(R.string.notif_channel_general_desc)
            }

            val birthdayChannel = NotificationChannel(
                CHANNEL_BIRTHDAY,
                getString(R.string.notif_channel_birthday_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = getString(R.string.notif_channel_birthday_desc)
            }

            manager.createNotificationChannel(generalChannel)
            manager.createNotificationChannel(birthdayChannel)
        }
    }

    companion object {
        const val CHANNEL_GENERAL = "channel_general"
        const val CHANNEL_BIRTHDAY = "channel_birthday"
    }
}
