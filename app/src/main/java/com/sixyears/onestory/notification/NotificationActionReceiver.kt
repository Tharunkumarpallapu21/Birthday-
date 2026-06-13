package com.sixyears.onestory.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.sixyears.onestory.ui.MainActivity

/**
 * Generic receiver for notification-related broadcast actions.
 * Currently used as a fallback to open the app when a notification is tapped,
 * in case a PendingIntent activity launch isn't used directly.
 */
class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val launchIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        ContextCompat.startActivity(context, launchIntent, null)
    }
}
