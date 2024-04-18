package com.hutapp.org.notes.hut.android.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.myUiComponent.MyItemBox
import com.hutapp.org.notes.hut.android.ui.myUiComponent.MyLazyForItemNotes
import com.hutapp.org.notes.hut.android.ui.navigation.Screens
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.CurrentScreenViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.TabItemList
import com.hutapp.org.notes.hut.android.ui.tabRow.TabRowCurrentItemViewModel

@Composable
fun NoteLazyScreen(
    modifier: Modifier = Modifier,
    index: Int,
    tabItemList: TabItemList,
    tabRowCurrentItemViewModel: TabRowCurrentItemViewModel,
    currentScreenViewModel: CurrentScreenViewModel,
    isShowDeleteInTrashItem: Boolean,
    noteViewModel: NoteViewModel,
    onFABclickListener: () -> Unit,
    onItemClickListener: (NoteEntity) -> Unit
) {

    MyLazyForItemNotes(
        noteViewModel = noteViewModel,
        isShowDeleteInTrashItem = isShowDeleteInTrashItem,
        onItemClickListener = onItemClickListener,
        currentScreenViewModel = currentScreenViewModel,
        tabRowCurrentItemViewModel = tabRowCurrentItemViewModel,
        tabItemList = tabItemList,
        index = index,
        onFABclickListener = onFABclickListener
    )
}






