package com.hutapp.org.notes.hut.android.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.theme.NotesHutAndroidTheme

@Composable
fun ReadNoteScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    noteViewModel: NoteViewModel,
    noteEntityId: Int,
    onFABClickListener: () -> Unit = {}
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = modifier.padding(16.dp),
                text = getNoteEntityFromId(
                    context = context,
                    noteEntityId = noteEntityId,
                    noteViewModel = noteViewModel
                ).labelNote,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                text = getNoteEntityFromId(
                    context = context,
                    noteEntityId = noteEntityId,
                    noteViewModel = noteViewModel
                ).message
            )
        }
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(onClick = {
                val noteEntity = getNoteEntityFromId(
                    context = context,
                    noteEntityId = noteEntityId,
                    noteViewModel = noteViewModel
                )

                // delete from db
//                noteViewModel.deleteNoteFromDB(
//                    noteEntity = getNoteEntityFromId(
//                        context = context,
//                        noteEntityId = noteEntityId,
//                        noteViewModel = noteViewModel
//                    )
//                )

                val newNoteEntity = noteEntity.copy(isDelete = true)
                noteViewModel.updateNote(noteEntity = newNoteEntity)
                onFABClickListener()
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }
        }
    }
}


private fun getNoteEntityFromId(
    context: Context,
    noteEntityId: Int,
    noteViewModel: NoteViewModel
): NoteEntity {
    return noteViewModel.noteList.value?.first {
        it.id == noteEntityId
    } ?: NoteEntity(
        labelNote = context.getString(R.string.no_label),
        message = context.getString(R.string.no_message),
        labelNoteScreen = context.getString(R.string.no_text),
        localDate = ""
    )
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun Preview() {
    NotesHutAndroidTheme {
        ReadNoteScreen(
            paddingValues = PaddingValues(),
            noteViewModel = viewModel(),
            noteEntityId = 0,
        )
    }
}