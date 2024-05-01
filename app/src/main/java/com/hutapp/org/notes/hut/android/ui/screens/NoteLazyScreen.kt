package com.hutapp.org.notes.hut.android.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.myUiComponent.MyLazyForItemNotes
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.CurrentScreenViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.TabItemList

@Composable
fun NoteLazyScreen(
    modifier: Modifier = Modifier,
    idEntity: Int?,
    index: Int,
    idEntity2: (Boolean) -> Int?,
    iss: Boolean,
    tabItemList: TabItemList,
    currentScreenViewModel: CurrentScreenViewModel,
    isShowDeleteInTrashItem: Boolean,
    noteViewModel: NoteViewModel,
    onItemClickListener: (NoteEntity) -> Unit,
    setIss: (Boolean) -> Unit
) {
    val listEntity = noteViewModel.noteList.observeAsState()
    val labelScreen = tabItemList.listItem[index]


    listEntity.value?.let { oldListEntity ->
        val filterList = oldListEntity.filter {
            it.labelNoteScreen == labelScreen.title && it.isDelete == isShowDeleteInTrashItem
        }
        idEntity2(true)?.let { id ->

            val entity = filterList.first {
                it.id == id
            }
            onItemClickListener(entity)
            idEntity2(false)
        }

        MyLazyForItemNotes(
            filterList = filterList,
            onItemClickListener = onItemClickListener,
            currentScreenViewModel = currentScreenViewModel,
        )
    }
}






