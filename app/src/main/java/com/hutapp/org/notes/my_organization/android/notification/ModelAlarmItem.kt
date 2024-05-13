package com.hutapp.org.notes.my_organization.android.notification

data class ModelAlarmItem(
    val id: Int,
    val time: Long,
    val message: String
) {
    companion object {
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_ID = "id message"
    }
}
