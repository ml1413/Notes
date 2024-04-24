package com.hutapp.org.notes.hut.android.notification

interface AlarmScheduler {
    fun scheduler(item: ModelAlarmItem)
    fun cancel(item: ModelAlarmItem)
}