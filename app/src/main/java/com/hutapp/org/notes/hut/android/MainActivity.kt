package com.hutapp.org.notes.hut.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.drawerSheet.DrawerItemStateViewModel
import com.hutapp.org.notes.hut.android.ui.navigation.NavigationScreen
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.TitleForTopBarViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.TabItemList
import com.hutapp.org.notes.hut.android.ui.tabRow.TabRowCurrentItemViewModel
import com.hutapp.org.notes.hut.android.ui.theme.NotesHutAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        setContent {
            NotesHutAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val drawerItemStateViewModel: DrawerItemStateViewModel = viewModel()
    val tabRowCurrentItemViewModel: TabRowCurrentItemViewModel = viewModel()
    val titleForTopBarViewModel: TitleForTopBarViewModel = viewModel()
    val noteViewModel: NoteViewModel = viewModel()
    val context = LocalContext.current
    val tabItemList: TabItemList = TabItemList(context = context)
    NavigationScreen(
        noteViewModel = noteViewModel,
        tabItemList = tabItemList,
        tabRowCurrentItemViewModel = tabRowCurrentItemViewModel,
        drawerItemStateViewModel = drawerItemStateViewModel,
        titleForTopBarViewModel = titleForTopBarViewModel
    )

}