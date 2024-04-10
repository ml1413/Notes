package com.i.blocknote.ui.tabRow

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.i.blocknote.R
import com.i.blocknote.ui.navigation.Screens

class TabRowStateViewModel : ViewModel() {
    val listTabItem  = listOf(
        ModelTabRowItem(
            title = R.string.note,
            screen = Screens.NoteScreen
        ),
        ModelTabRowItem(
            title = R.string.reminding,
            screen = Screens.RemindersScreen
        ),
        ModelTabRowItem(
            title = R.string.bookmarks,
            screen = Screens.BookmarksScreen
        )
    )
    val selectedItem = mutableStateOf(0)


}