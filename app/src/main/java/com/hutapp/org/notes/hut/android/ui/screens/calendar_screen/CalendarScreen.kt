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
            MyItemBox(
                currentScreenViewModel = currentScreenViewModel,
                noteEntity = noteEntity,
            )
//            Box(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 2.dp)
//                    .clickable { }
//                    .border(
//                        width = 1.dp,
//                        shape = RoundedCornerShape(8.dp),
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//            ) {
//                Column(
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp, vertical = 4.dp)
//                ) {
//                    Text(
//                        text = noteEntity.labelNote,
//                        maxLines = 1,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Text(
//                        maxLines = 1,
//                        text = noteEntity.message
//                    )
//                }
//            }
        }
    }
}


