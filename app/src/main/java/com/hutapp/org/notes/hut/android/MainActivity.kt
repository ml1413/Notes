package com.hutapp.org.notes.hut.android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.notification.AlarmSchedulerImpl
import com.hutapp.org.notes.hut.android.ui.drawerSheet.DrawerItemStateViewModel
import com.hutapp.org.notes.hut.android.ui.navigation.NavigationScreen
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.CurrentScreenViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.TabItemList
import com.hutapp.org.notes.hut.android.ui.tabRow.TabRowCurrentItemViewModel
import com.hutapp.org.notes.hut.android.ui.theme.NotesHutAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var analytics: FirebaseAnalytics

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        installSplashScreen()
        setContent {

            NotesHutAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val launchPermissionNotification = rememberPermissionState(
                        permission =
                        android.Manifest.permission.POST_NOTIFICATIONS
                    )

                    SideEffect {
                        if (!launchPermissionNotification.status.isGranted) {
                            launchPermissionNotification.launchPermissionRequest()
                        }
                    }
                    MainScreen(launchPermissionNotification = launchPermissionNotification)
                }
            }
        }
    }


    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun MainScreen(
        modifier: Modifier = Modifier,
        launchPermissionNotification: PermissionState
    ) {
        val drawerItemStateViewModel: DrawerItemStateViewModel = viewModel()
        val tabRowCurrentItemViewModel: TabRowCurrentItemViewModel = viewModel()
        val currentScreenViewModel: CurrentScreenViewModel = viewModel()
        val noteViewModel: NoteViewModel = viewModel()
        val context = LocalContext.current
        val tabItemList: TabItemList = TabItemList(context = context)
        val alarmSchedulerImpl = AlarmSchedulerImpl(context = context)
        NavigationScreen(
            launchPermissionNotification = launchPermissionNotification,
            noteViewModel = noteViewModel,
            tabItemList = tabItemList,
            alarmSchedulerImpl = alarmSchedulerImpl,
            tabRowCurrentItemViewModel = tabRowCurrentItemViewModel,
            drawerItemStateViewModel = drawerItemStateViewModel,
            currentScreenViewModel = currentScreenViewModel
        )

    }
}