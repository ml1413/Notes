package com.i.blocknote.ui.drawerSheet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.i.blocknote.R
import com.i.blocknote.ui.navigation.Screens

class DrawerItemStateViewModel : ViewModel() {
    val listMenu = listOf(
        ModelDrawerItem(
            title = R.string.all_note,
            screens = Screens.AllNotesScreen
        ),
        ModelDrawerItem(
            title = R.string.trash,
            screens = Screens.TrashScreen
        ),
        ModelDrawerItem(
            title = R.string.settirng,
            screens = Screens.SettingsScreen
        )
    )

    val selectedItemDrawState = mutableStateOf<ModelDrawerItem?>(null)


}