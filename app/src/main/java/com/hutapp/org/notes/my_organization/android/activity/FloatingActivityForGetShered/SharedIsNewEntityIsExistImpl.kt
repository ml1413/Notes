package com.hutapp.org.notes.my_organization.android.activity.FloatingActivityForGetShered

import android.content.SharedPreferences
import javax.inject.Inject

class SharedIsNewEntityIsExistImpl @Inject constructor(
    private val shared: SharedPreferences
) : NewEntityIsExist {
    companion object {
        const val SHARED_NAME = "my shared"
        const val KEY_FOR_BOOLEAN = "key boolean"
    }

    override fun entityHasSave(isExist: Boolean) {
        shared.edit().apply {
            this.putBoolean(KEY_FOR_BOOLEAN, isExist)
            apply()
        }
    }

    override fun newEntityIsExist(): Boolean {
        return shared.getBoolean(KEY_FOR_BOOLEAN, false)
    }
}