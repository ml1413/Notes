package com.hutapp.org.notes.hut.android.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.hutapp.org.notes.hut.android.activity.MainActivity
import com.hutapp.org.notes.hut.android.R

const val NOTIFICATION_CHANEL_ID = "my chanel"
const val NOTIFICATION_NAME = "my notification"
const val ID_ENTITY = "id entity"

class MyNotification(private val context: Context) {
    private val manager =
        context.getSystemService(NotificationManager::class.java)

    fun showNotification(labelNotification: String, idNotification: Int = 1) {
        val chanel = NotificationChannel(
            NOTIFICATION_CHANEL_ID,
            NOTIFICATION_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        manager.createNotificationChannel(chanel)

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(ID_ENTITY, idNotification)
        val pendingIntent = PendingIntent.getActivity(
            context,
            idNotification,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANEL_ID)
            .setContentTitle(labelNotification)
            .setSmallIcon(R.drawable.baseline_edit_note_24)
            .setContentIntent(pendingIntent)
            .build()

        manager.notify(idNotification, notification)
    }
}