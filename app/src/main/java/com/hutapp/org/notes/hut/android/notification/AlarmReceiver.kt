package com.hutapp.org.notes.hut.android.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    private var myNotification: MyNotification? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getIntExtra(ModelAlarmItem.EXTRA_ID, 0) ?: 0
        val message = intent?.getStringExtra(ModelAlarmItem.EXTRA_MESSAGE) ?: ""

        myNotification = context?.let { MyNotification(it) }

        myNotification?.showNotification(labelNotification = message, idNotification = id)

    }
}