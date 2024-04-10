package com.i.blocknote.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.i.blocknote.R
import com.i.blocknote.db.NoteEntity
import com.i.blocknote.db.NoteViewModel

@Composable
fun AddInfoScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    noteViewModel: NoteViewModel,
    onFABclickListener: () -> Unit = {}
) {
    val textLabel = remember {
        mutableStateOf("")
    }
    val textMessage = remember {
        mutableStateOf("")
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        Column(modifier = modifier.fillMaxSize()) {
            TextField(
                modifier = modifier.fillMaxWidth(),
                singleLine = true,
                label = {
                    Text(text = stringResource(R.string.label))
                },
                value = textLabel.value,
                onValueChange = {
                    textLabel.value = it
                }
            )
            TextField(
                modifier = modifier.fillMaxSize(),
                value = textMessage.value,
                label = {
                    Text(text = stringResource(R.string.text))
                },
                onValueChange = {
                    textMessage.value = it
                })
        }
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                modifier = modifier.padding(16.dp),
                onClick = {
                    val noteEntity = NoteEntity(
                        label = textLabel.value,
                        message = textMessage.value
                    )
                    noteViewModel.addNoteEntityInDB(noteEntity = noteEntity)
                    onFABclickListener()
                },
                content = {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                })
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun preview() {
    AddInfoScreen(
        paddingValues = PaddingValues(),
        noteViewModel = viewModel()
    )
}