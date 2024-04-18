package com.hutapp.org.notes.hut.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
                    MyItemBox(onItemClickListener = onItemClickListener, noteEntity = noteEntity)
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

@Composable
private fun MyItemBox(
    modifier: Modifier = Modifier,
    onItemClickListener: (NoteEntity) -> Unit,
    noteEntity: NoteEntity
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
            .clickable { onItemClickListener(noteEntity) },
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = modifier.padding(8.dp),
                    text = maxLengthString(string = noteEntity.labelNote, maxLength = 10),
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )

            }
            Box(
                modifier =
                modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    modifier =
                    modifier.padding(8.dp),
                    maxLines = 1,
                    text = maxLengthString(string = noteEntity.message, maxLength = 15)
                )
            }

        }
    }
}

private fun maxLengthString(string: String, maxLength: Int): String {
    return if (string.length > maxLength) string.substring(0, maxLength) + "..."
    else string
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun Preview(
    modifier: Modifier = Modifier,
    onItemClickListener: (NoteEntity) -> Unit = {}
) {
    val noteEntity = NoteEntity(
        id = 3,
        labelNoteScreen = "Label",
        labelNote = "Label note,",
        isDelete = false,
        message = "fsdfdsf dfs df s f dsgf d gsd g d g sd fd f",
        localDate = "12-3-2024"
    )
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onItemClickListener(noteEntity) },
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {

            Text(
                modifier = modifier.padding(8.dp),
                text = noteEntity.labelNote,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier =
                modifier.padding(8.dp),
                maxLines = 1,
                text = noteEntity.message
            )
        }
    }
}
