package com.hutapp.org.notes.my_organization.android.ui.screens.calendar_screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.my_organization.android.db.NoteEntity
import com.hutapp.org.notes.my_organization.android.db.NoteViewModel
import com.hutapp.org.notes.my_organization.android.ui.myComponent.MyItemBox
import com.hutapp.org.notes.my_organization.android.ui.tabRow.MyTopBar.CurrentScreenViewModel

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
    val choiceDate = rememberSaveable { mutableStateOf("") }

    Log.d("TAG1", "CalendarScreen: ")

    BackHandler { onBackListener() }
    LazyColumn(
        modifier = modifier.padding(paddingValues),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            MyCalendar(modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
                listEntity = listEntity,
                onItemClickListener = { localDate ->
                    choiceDate.value = localDate.toString()
                }
            )
        }
        listEntity.value?.let { listNoteEntity ->
            val listNotes = listNoteEntity.filter {
                it.addNoteDate == choiceDate.value && !it.isDelete
            }
            items(listNotes) { noteEntity ->
                Log.d("TAG1", "items: ${listNotes.size}")
                MyItemBox(
                    currentScreenViewModel = currentScreenViewModel,
                    noteEntity = noteEntity,
                    onItemClickListener = onItemClickListener
                )
            }
        }
    }
}


