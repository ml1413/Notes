package com.hutapp.org.notes.hut.android.ui.screens

import android.app.Activity
import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.myUiComponent.MyFAB
import com.hutapp.org.notes.hut.android.ui.tabRow.TabRowCurrentItemViewModel
import java.time.LocalDate

@Composable
fun AddInfoScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    noteViewModel: NoteViewModel,
    tabRowCurrentItemViewModel: TabRowCurrentItemViewModel,
    onFABclickListener: () -> Unit = {}
) {
    val currentLabelScreen =
        tabRowCurrentItemViewModel.currentItem.value?.title
            ?: stringResource(id = R.string.no_text)
    val textLabel = rememberSaveable {
        mutableStateOf("")
    }
    val textMessage = rememberSaveable {
        mutableStateOf("")
    }
    val isError = remember {
        mutableStateOf(false)
    }
    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(key1 = null) {
        focusRequester.requestFocus()
    }
    //resizeWindow__________________________________________________________________________________
    val activity: Activity = LocalContext.current as Activity
    //______________________________________________________________________________________________

    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        TextField(
            modifier = modifier
                .focusRequester(focusRequester = focusRequester)
                .onFocusChanged {
                    //resizeWindow__________________________________________________________________
                    activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                }
                .fillMaxWidth(),
            isError = isError.value,
            singleLine = true,
            label = {
                Text(text = stringResource(R.string.label))
            },
            value = textLabel.value,
            onValueChange = {
                isError.value = false
                textLabel.value = it
            }
        )
        TextField(
            modifier = modifier.fillMaxSize(),
            isError = isError.value,
            value = textMessage.value,
            label = {
                Text(text = stringResource(R.string.text))
            },
            onValueChange = {
                isError.value = false
                textMessage.value = it
            })
    }
    MyFAB(iconForFAB = Icons.Default.Done, onFABClisk = {
        if (textLabel.value.isNotBlank() && textMessage.value.isNotBlank()) {
            val noteEntity = NoteEntity(
                labelNote = textLabel.value.capitalize(Locale.current),
                message = textMessage.value,
                labelNoteScreen = currentLabelScreen,
                localDate = LocalDate.now().toString()
            )
            noteViewModel.addNoteEntityInDB(noteEntity = noteEntity)
            onFABclickListener()
        } else {
            isError.value = true
        }
    })
}
