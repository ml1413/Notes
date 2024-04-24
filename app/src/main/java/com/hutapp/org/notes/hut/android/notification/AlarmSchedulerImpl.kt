package com.hutapp.org.notes.hut.android.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class AlarmSchedulerImpl(private val context: Context) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun scheduler(item: ModelAlarmItem) {

        val intent = Intent(context, AlarmReceiver::class.java)
        putExtra(item = item, intent = intent)

        val pendingIntent = getPendingIntent(intent = intent)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, item.time, pendingIntent)
    }


    override fun cancel(item: ModelAlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java)

        val pendingIntent = getPendingIntent(intent = intent)

        alarmManager.cancel(pendingIntent)
    }

    /** other________________________________________________________________________________________*/
    private fun putExtra(item: ModelAlarmItem, intent: Intent): Intent {
        intent.putExtra(ModelAlarmItem.EXTRA_MESSAGE, item.message)
        intent.putExtra(ModelAlarmItem.EXTRA_ID, item.id)
        return intent
    }

    private fun getPendingIntent(intent: Intent) = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

}