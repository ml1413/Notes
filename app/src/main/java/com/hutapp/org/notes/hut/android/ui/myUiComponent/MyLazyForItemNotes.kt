package com.hutapp.org.notes.hut.android.ui.myUiComponent

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.CurrentScreenViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.TabItemList

@Composable
fun MyLazyForItemNotes(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    noteViewModel: NoteViewModel,
    isShowDeleteInTrashItem: Boolean,
    currentScreenViewModel: CurrentScreenViewModel,
    tabItemList: TabItemList,
    index: Int = 0,
    onItemIconClickListener: (NoteEntity) -> Unit = {},
    onItemClickListener: (NoteEntity) -> Unit,
) {
    val listEntity = noteViewModel.noteList.observeAsState()
    val labelScreen = tabItemList.listItem[index]
    Log.d("TAG1", "listEntity: ${listEntity.value?.size}")
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {

            listEntity.value?.let { listEntity ->
                val filterList = listEntity.filter {
                    Log.d("TAG1", "it.labelNoteScreen ${it.labelNoteScreen} labelScreen.title${labelScreen.title}")
                    it.labelNoteScreen == labelScreen.title && it.isDelete == isShowDeleteInTrashItem
                }
                items(filterList) { noteEntity ->
                    MyItemBox(
                        onItemClickListener = onItemClickListener,
                        noteEntity = noteEntity,
                        currentScreenViewModel = currentScreenViewModel,
                        onIconButtonClickListener = { onItemIconClickListener(noteEntity) }
                    )
                }
            }
        }

    }
}