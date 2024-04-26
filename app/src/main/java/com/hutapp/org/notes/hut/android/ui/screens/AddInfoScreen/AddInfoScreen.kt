package com.hutapp.org.notes.hut.android.ui.screens.AddInfoScreen

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.notification.AlarmSchedulerImpl
import com.hutapp.org.notes.hut.android.notification.ModelAlarmItem
import com.hutapp.org.notes.hut.android.ui.myUiComponent.MyFAB
import com.hutapp.org.notes.hut.android.ui.tabRow.TabRowCurrentItemViewModel
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun AddInfoScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    alarmSchedulerImpl: AlarmSchedulerImpl,
    noteViewModel: NoteViewModel,
    tabRowCurrentItemViewModel: TabRowCurrentItemViewModel,
    onFABclickListener: () -> Unit = {},
) {
    val owner = LocalLifecycleOwner.current

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
    val isShowAlert = rememberSaveable {
        mutableStateOf(false)
    }
    val noteEntityForSave: MutableState<NoteEntity?> = remember {
        mutableStateOf(null)
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
    /** add notification ________________________________________________*/
    val reminderScreenLabel = stringResource(id = R.string.reminding)
    /** add notification ________________________________________________*/
    MyFAB(iconForFAB = Icons.Default.Done, onFABClisk = {
        if (textLabel.value.isNotBlank() && textMessage.value.isNotBlank()) {
            noteEntityForSave.value = NoteEntity(
                labelNote = textLabel.value.capitalize(Locale.current),
                message = textMessage.value,
                labelNoteScreen = currentLabelScreen,
                addNoteDate = LocalDate.now(ZoneId.systemDefault()).toString()
            )

            if (currentLabelScreen == reminderScreenLabel) {
                isShowAlert.value = true
            } else {
                noteEntityForSave.value?.let { noteEntity ->
                    noteViewModel.addNoteEntityInDB(noteEntity = noteEntity)
                }
                onFABclickListener()
            }
        } else {
            isError.value = true
        }
    })
    /** add notification ________________________________________________*/
    if (isShowAlert.value) {
        MyAlertPicker(
            onDismissRequest = { isShowAlert.value = false },
            onDoneClickListener = { timeMilesForNotification ->

                noteEntityForSave.value?.let { noteEntity ->
                    val currentTime = System.currentTimeMillis()
                    if (currentTime < timeMilesForNotification) {
                        // add time notification in entity
                        val noteForNotification = noteEntity
                            .copy(timeNotification = timeMilesForNotification)

                        noteViewModel.addNoteEntityInDB(noteEntity = noteForNotification)
                        // set notification in alarm ______________________________________________


                        noteViewModel.noteList.observe(owner) { listNote ->
                            val findNote =
                                listNote.find { it.timeNotification == timeMilesForNotification }

                            findNote?.let { note ->
                                if (note.id != null && note.timeNotification != null) {
                                    val currentIme = System.currentTimeMillis()
                                    if (currentIme < timeMilesForNotification) {
                                        val item = ModelAlarmItem(
                                            id = note.id,
                                            time = note.timeNotification,
                                            message = note.labelNote
                                        )
                                        alarmSchedulerImpl.scheduler(item = item)
                                    }
                                }
                                onFABclickListener()
                            }
                        }
                    }
                }

            }
        )
        /** add notification ________________________________________________*/
    }
}


