package com.hutapp.org.notes.my_organization.android.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmSchedulerImpl(private val context: Context) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun scheduler(item: ModelAlarmItem) {
        Log.d("TAG1", "scheduler: ${item.id} ")

        val intent = Intent(context, AlarmReceiver::class.java)
        putExtra(item = item, intent = intent)

        val pendingIntent = getPendingIntent(intent = intent, itemId = item.id)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, item.time, pendingIntent)
    }


    override fun cancel(itemId: Int) {
        val intent = Intent(context, AlarmReceiver::class.java)
        Log.d("TAG1", "cancel: $itemId ")
        val pendingIntent = getPendingIntent(intent = intent, itemId = itemId)

        alarmManager.cancel(pendingIntent)
    }

    /** other________________________________________________________________________________________*/
    private fun putExtra(item: ModelAlarmItem, intent: Intent): Intent {
        intent.putExtra(ModelAlarmItem.EXTRA_MESSAGE, item.message)
        intent.putExtra(ModelAlarmItem.EXTRA_ID, item.id)
        return intent
    }

    private fun getPendingIntent(intent: Intent, itemId: Int) = PendingIntent.getBroadcast(
        context,
        itemId,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

}