package com.hutapp.org.notes.hut.android.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.myComponent.MyLazyForItemNotes
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.CurrentScreenViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.TabItemList
import kotlinx.coroutines.CoroutineScope

@Composable
fun NoteLazyScreen(
    modifier: Modifier = Modifier,
    index: Int,
    coroutineScope: CoroutineScope,
    idEntity2: (Boolean) -> Int?,
    tabItemList: TabItemList,
    currentScreenViewModel: CurrentScreenViewModel,
    isShowDeleteInTrashItem: Boolean,
    noteViewModel: NoteViewModel,
    onItemClickListener: (NoteEntity) -> Unit,
) {
    val listEntity = noteViewModel.noteList.observeAsState()
    val labelScreen = tabItemList.listItem[index]


    listEntity.value?.let { oldListEntity ->
        val filterList = oldListEntity.filter {
            it.labelNoteScreen == labelScreen.title && it.isDelete == isShowDeleteInTrashItem
        }

        MyLazyForItemNotes(
            idEntity2 = idEntity2,
            coroutineScope = coroutineScope,
            filterList = filterList,
            onItemClickListener = onItemClickListener,
            currentScreenViewModel = currentScreenViewModel,
        )
    }
}






