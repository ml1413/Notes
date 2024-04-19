package com.hutapp.org.notes.hut.android.ui.screens.trash_screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hutapp.org.notes.hut.android.alert.MyAlert
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.myUiComponent.MyLazyForItemNotes
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.CurrentScreenViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.TabItemList
import com.hutapp.org.notes.hut.android.ui.tabRow.TabRowCurrentItemViewModel

@Composable
fun TrashScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    currentScreenViewModel: CurrentScreenViewModel,
    tabItemList: TabItemList,
    noteViewModel: NoteViewModel,
    itemForDeleteViewModel: ItemForDeleteViewModel = viewModel(),
    isShowDeleteInTrashItem: Boolean,
    onItemClickListener: (NoteEntity) -> Unit,
    onBackHandler: () -> Unit

) {
    BackHandler { onBackHandler() }
    val alertIsVisible = rememberSaveable { mutableStateOf(false) }

    if (alertIsVisible.value) {
        MyAlert(
            onDismiss = { alertIsVisible.value = false },
            onDelete = {
                itemForDeleteViewModel.currentEntity.value?.let { noteEntity ->
                    noteViewModel.deleteNoteFromDB(noteEntity = noteEntity)
                }
            },
            onRestore = {
                itemForDeleteViewModel.currentEntity.value?.let { noteEntity ->
                    val noteForRestore = noteEntity.copy(isDelete = false)
                    noteViewModel.updateNote(noteEntity = noteForRestore)
                }
            })
    }

    MyLazyForItemNotes(
        paddingValues = paddingValues,
        noteViewModel = noteViewModel,
        isShowDeleteInTrashItem = isShowDeleteInTrashItem,
        currentScreenViewModel = currentScreenViewModel,
        tabItemList = tabItemList,
        index = 0,
        onItemClickListener = onItemClickListener,
        onItemIconClickListener = { noteEntity ->
            itemForDeleteViewModel.setValue(noteEntity = noteEntity)
            alertIsVisible.value = true
        }
    )
}
