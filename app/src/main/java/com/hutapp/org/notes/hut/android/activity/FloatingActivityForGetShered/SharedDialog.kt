package com.hutapp.org.notes.hut.android.activity.FloatingActivityForGetShered

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.ui.myComponent.MyFAB
import com.hutapp.org.notes.hut.android.ui.theme.NotesHutAndroidTheme
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun SharedDialog(
    modifier: Modifier = Modifier,
    textFromIntent: String = "",
    onFABClick: (NoteEntity) -> Unit = {}
) {
    val labelNote = remember {
        mutableStateOf("")
    }
    val scrollState = rememberScrollState(0)
    val isError = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxSize()

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = modifier.padding(8.dp),
                text = stringResource(id = R.string.bookmarks),
                color = MaterialTheme.colorScheme.background
            )
        }
        TextField(
            modifier = modifier
                .fillMaxWidth(),
            isError = isError.value,
            label = {
                Text(text = stringResource(id = R.string.label))
            },
            singleLine = true,
            value = labelNote.value,
            onValueChange = {
                isError.value = false
                labelNote.value = it
            })
        Text(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(state = scrollState),
            text = textFromIntent
        )


    }
    val labelScreen = stringResource(id = R.string.bookmarks)
    MyFAB(iconForFAB = Icons.Default.Done,
        onFABClisk = {
            isError.value = labelNote.value.isBlank() or textFromIntent.isBlank()
            if (!isError.value) {
                val noteEntity = NoteEntity(
                    labelNoteScreen = labelScreen,
                    labelNote = labelNote.value,
                    message = textFromIntent,
                    addNoteDate = LocalDate.now(ZoneId.systemDefault()).toString()
                )
                onFABClick(noteEntity)
            }
        })
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GreetingPreview() {
    NotesHutAndroidTheme {
        SharedDialog()
    }
}