package com.hutapp.org.notes.hut.android.utilsFun

import android.app.Activity
import androidx.core.view.WindowCompat

fun Activity.setResize(boolean: Boolean) {
    WindowCompat.setDecorFitsSystemWindows(this.window, boolean)
}