package com.hutapp.org.notes.my_organization.android.ui.screens.readNoteScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.my_organization.android.R
import com.hutapp.org.notes.my_organization.android.db.NoteEntity
import com.hutapp.org.notes.my_organization.android.db.NoteViewModel
import com.hutapp.org.notes.my_organization.android.notification.AlarmSchedulerImpl
import com.hutapp.org.notes.my_organization.android.ui.myComponent.MyFAB

@Composable
fun ReadNoteScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    alarmSchedulerImpl: AlarmSchedulerImpl,
    noteViewModel: NoteViewModel,
    readNoteViewModel: ReadNoteViewModel,
    onFABClickListener: () -> Unit = {}
) {

    val context = LocalContext.current
    val currentNote = readNoteViewModel.noteEntity.value ?: NoteEntity(
        labelNote = context.getString(R.string.no_label),
        message = context.getString(R.string.no_message),
        labelNoteScreen = context.getString(R.string.no_text),
        addNoteDate = ""
    )
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
                text = currentNote.labelNote,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
            Text(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                text = currentNote.message
            )
        }
        MyFAB(iconForFAB = Icons.Default.Delete, onFABClisk = {
            // mark delete entity
            val noteForDeleteInTrash = currentNote.copy(isDelete = true)
            noteViewModel.updateNote(noteEntity = noteForDeleteInTrash)
            // cancel notification
            noteForDeleteInTrash.apply {
                if (timeNotification != null)
                    id?.let { noteId ->
                        alarmSchedulerImpl.cancel(itemId = noteId)
                    }
            }
            onFABClickListener()
        })
    }
}

