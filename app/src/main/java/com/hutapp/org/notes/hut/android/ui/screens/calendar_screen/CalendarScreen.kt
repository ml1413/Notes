package com.hutapp.org.notes.hut.android.ui.screens.calendar_screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.myUiComponent.MyItemBox
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.CurrentScreenViewModel

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    noteViewModel: NoteViewModel,
    currentScreenViewModel: CurrentScreenViewModel,
    paddingValues: PaddingValues = PaddingValues(),
    onItemClickListener: (NoteEntity) -> Unit,
    onBackListener: () -> Unit = {}
) {
    val listEntity = noteViewModel.noteList.observeAsState()
    val choiceLocalDate = remember { mutableStateOf("") }
    Log.d("TAG1", "CalendarScreen: ")

    BackHandler { onBackListener() }
    LazyColumn(
        modifier = modifier.padding(paddingValues),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            MyCalendar(
                listEntity = listEntity,
                noteViewModel = noteViewModel,
                onItemClickListener = { localDate ->
                    choiceLocalDate.value = localDate.toString()
                }
            )
        }
        noteViewModel.noteList.value?.let { listNoteEntity ->
            val listNotes = listNoteEntity.filter {
                it.localDate == choiceLocalDate.value && !it.isDelete
            }
            Log.d("TAG1", "filter: ")
            items(listNotes) { noteEntity ->
                MyItemBox(
                    currentScreenViewModel = currentScreenViewModel,
                    noteEntity = noteEntity,
                    onItemClickListener = onItemClickListener
                )
            }
        }
    }
}


