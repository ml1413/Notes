package com.hutapp.org.notes.my_organization.android.utilsFun

import android.app.Activity
import androidx.core.view.WindowCompat

fun Activity.setResize(boolean: Boolean) {
    WindowCompat.setDecorFitsSystemWindows(this.window, boolean)
}