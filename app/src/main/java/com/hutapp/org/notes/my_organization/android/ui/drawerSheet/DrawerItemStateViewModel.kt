package com.hutapp.org.notes.my_organization.android.ui.drawerSheet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hutapp.org.notes.my_organization.android.ui.navigation.Screens

class DrawerItemStateViewModel : ViewModel() {
    val listMenu = listOf(
        Screens.AllNotesScreen,
        Screens.TrashScreen,
        Screens.SettingsScreen
    )

    val selectedItemDrawState = mutableStateOf<Screens>(listMenu[0])


}