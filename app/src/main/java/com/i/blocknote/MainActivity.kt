package com.i.blocknote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.i.blocknote.db.NoteViewModel
import com.i.blocknote.ui.drawerSheet.DrawerItemStateViewModel
import com.i.blocknote.ui.drawerSheet.MyDrawerSheet
import com.i.blocknote.ui.navigation.NavigationScreen
import com.i.blocknote.ui.tabRow.TabRowStateViewModel
import com.i.blocknote.ui.theme.BlocknoteTheme
import com.i.blocknote.ui.theme.MyTopBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BlocknoteTheme {
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
    val tabRowStateViewModel : TabRowStateViewModel = viewModel()
    val noteViewModel :NoteViewModel = viewModel()
    NavigationScreen(
        noteViewModel = noteViewModel,
        tabRowStateViewModel = tabRowStateViewModel,
        drawerItemStateViewModel = drawerItemStateViewModel
    )


}


