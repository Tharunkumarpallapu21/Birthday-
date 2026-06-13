package com.sixyears.onestory.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.sixyears.onestory.R
import com.sixyears.onestory.SixYearsApp
import com.sixyears.onestory.ui.MainActivity

object NotificationHelper {

    const val NOTIF_ID_GOOD_MORNING = 1001
    const val NOTIF_ID_COUNTDOWN = 1002
    const val NOTIF_ID_BIRTHDAY_MIDNIGHT = 2001
    const val NOTIF_ID_BIRTHDAY_MORNING = 2002
    const val NOTIF_ID_BIRTHDAY_NOON = 2003
    const val NOTIF_ID_BIRTHDAY_EVENING = 2004

    /**
     * Shows a notification. Safe to call even if POST_NOTIFICATIONS permission
     * is not granted — it will simply be a no-op (checked before posting).
     */
    fun showNotification(
        context: Context,
        notificationId: Int,
        title: String,
        message: String,
        channelId: String = SixYearsApp.CHANNEL_GENERAL
    ) {
        val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

        if (!hasPermission) return

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_heart)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(notificationId, notification)
        } catch (_: SecurityException) {
            // Permission revoked between check and post — silently ignore.
        }
    }

    fun cancelAll(context: Context) {
        val manager = context.getSystemService(NotificationManager::class.java)
        manager?.cancelAll()
    }
}
