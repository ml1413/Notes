package com.hutapp.org.notes.hut.android.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.hutapp.org.notes.hut.android.R

const val NOTIFICATION_CHANEL_ID = "my chanel"
const val NOTIFICATION_NAME = "my notification"
class MyNotification(private val context: Context) {
    private val manager =
        context.getSystemService(NotificationManager::class.java)

    fun showNotification(labelNotification: String, idNotification: Int = 1) {
        //todo else notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chanel = NotificationChannel(
                NOTIFICATION_CHANEL_ID,
                NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            manager.createNotificationChannel(chanel)

            val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANEL_ID)
                .setContentTitle(labelNotification)
                .setSmallIcon(R.drawable.baseline_edit_note_24)
                .build()

            manager.notify(idNotification, notification)
        }
    }
}