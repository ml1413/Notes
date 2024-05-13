package com.hutapp.org.notes.my_organization.android.notification

interface AlarmScheduler {
    fun scheduler(item: ModelAlarmItem)
    fun cancel(itemId: Int)
}