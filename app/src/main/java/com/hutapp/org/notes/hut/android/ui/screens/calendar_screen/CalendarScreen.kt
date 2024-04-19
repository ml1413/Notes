package com.hutapp.org.notes.hut.android.ui.screens.calendar_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
    val listNotes = rememberSaveable {
        mutableStateOf(emptyList<NoteEntity>())
    }
    BackHandler { onBackListener() }
    LazyColumn(
        modifier = modifier.padding(paddingValues),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            MyCalendar(
                noteViewModel = noteViewModel,
                onItemClickListener = { localDate ->
                    noteViewModel.noteList.value?.let { listNoteEntity ->
                        listNotes.value =
                            listNoteEntity.filter { it.localDate == localDate.toString() }
                    }
                }
            )
        }
        items(listNotes.value) { noteEntity ->
            if (!noteEntity.isDelete)
                MyItemBox(
                    currentScreenViewModel = currentScreenViewModel,
                    noteEntity = noteEntity,
                    onItemClickListener = onItemClickListener
                )
        }
    }
}


