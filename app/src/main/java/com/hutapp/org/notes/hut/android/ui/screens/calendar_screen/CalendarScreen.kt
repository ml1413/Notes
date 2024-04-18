package com.hutapp.org.notes.hut.android.ui.screens.calendar_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    noteViewModel: NoteViewModel,
    paddingValues: PaddingValues = PaddingValues(),
    onBackListener: () -> Unit = {}
) {
    val listNotes = rememberSaveable {
        mutableStateOf(emptyList<NoteEntity>())
    }
    BackHandler { onBackListener() }
    LazyColumn(
        modifier = modifier.padding(paddingValues)
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
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp)
                    .clickable { }
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


