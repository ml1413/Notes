package com.hutapp.org.notes.hut.android.ui.myComponent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle

@Composable
fun MyLifecycle(
    onStarted: () -> Unit = {},
    initialized: () -> Unit = {},
    onCreate: () -> Unit = {},
    onDestroy: () -> Unit = {},
    onResume: () -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> onDestroy()
            Lifecycle.State.INITIALIZED -> initialized()
            Lifecycle.State.CREATED -> onCreate()
            Lifecycle.State.STARTED -> onStarted()
            Lifecycle.State.RESUMED -> onResume()
        }
    }
}
