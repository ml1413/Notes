package com.hutapp.org.notes.hut.android.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.TabItemList
import com.hutapp.org.notes.hut.android.ui.tabRow.TabRowCurrentItemViewModel

@Composable
fun NoteLazyScreen(
    modifier: Modifier = Modifier,
    index: Int,
    tabItemList: TabItemList,
    tabRowCurrentItemViewModel: TabRowCurrentItemViewModel,
    isShowDeleteInTrashItem: Boolean,
    noteViewModel: NoteViewModel,
    onFABclickListener: () -> Unit,
    onItemClickListener: (NoteEntity) -> Unit
) {
    val listEntity = noteViewModel.noteList.observeAsState()
    val labelScreen = tabItemList.listItem[index]
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //labelScreen___________________________________________________________________________
            item {
                Text(
                    modifier = modifier
                        .padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    text = labelScreen.title
                )
            }
            //______________________________________________________________________________________
            listEntity.value?.let { listEntity ->
                val filterList = listEntity.filter {
                    it.labelNoteScreen == labelScreen.title && it.isDelete == isShowDeleteInTrashItem
                }
                items(filterList) { noteEntity ->
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 2.dp)
                            .clickable { onItemClickListener(noteEntity) }
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = noteEntity.labelNote,
                                maxLines = 1,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                maxLines = 1,
                                text = noteEntity.message
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                modifier = modifier.padding(16.dp),
                onClick = {
                    tabRowCurrentItemViewModel.setItem(modelTabRowItem = tabItemList.listItem[index])
                    onFABclickListener()
                },
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                })
        }
    }
}
