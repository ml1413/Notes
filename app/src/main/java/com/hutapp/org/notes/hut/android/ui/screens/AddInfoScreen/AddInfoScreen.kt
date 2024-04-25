package com.hutapp.org.notes.hut.android.ui.screens.AddInfoScreen

import android.app.Activity
import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.notification.AlarmSchedulerImpl
import com.hutapp.org.notes.hut.android.notification.ModelAlarmItem
import com.hutapp.org.notes.hut.android.ui.myUiComponent.MyFAB
import com.hutapp.org.notes.hut.android.ui.screens.calendar_screen.MyCalendar
import com.hutapp.org.notes.hut.android.ui.tabRow.TabRowCurrentItemViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.Date

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
                localDate = LocalDate.now().toString()
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
        val context = LocalContext.current
        MyAlertPicker(
            onDismissRequest = { isShowAlert.value = false },
            onDoneClickListener = { timeMiles ->
                val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                Log.d("TAG1", "AddInfoScreen: ${sdf.format(Date(timeMiles))}")

                noteEntityForSave.value?.let { noteEntity ->
                    noteViewModel.addNoteEntityInDB(noteEntity = noteEntity)

                    val alarmSchedulerImpl = AlarmSchedulerImpl(context)

                    val modelAlarmItem = ModelAlarmItem(
                        id = noteEntity.id ?: 0,
                        time = timeMiles,
                        noteEntity.labelNote
                    )
                    alarmSchedulerImpl.scheduler(item = modelAlarmItem)
                }
                onFABclickListener()
            }
        )
        /** add notification ________________________________________________*/
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAlertPicker(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onDoneClickListener: (Long) -> Unit = {},
) {
    val localDateState = rememberSaveable { mutableStateOf(LocalDate.now()) }

    val localTime = LocalTime.now()
    val timePickerState =
        rememberTimePickerState(initialHour = localTime.hour, initialMinute = localTime.minute)

    val isShowTimePicker = remember { mutableStateOf(false) }
    val isShowDatePicker = remember { mutableStateOf(false) }

    val shape = RoundedCornerShape(8.dp)
    val labelDate = remember {
        mutableStateOf("")
    }
    SideEffect {
        labelDate.value =
            "${localDateState.value} ${timePickerState.hour}:" + formatMinute(minute = timePickerState.minute.toString())
    }

    PickerAlert(
        onDismissRequest = onDismissRequest,
        onDoneClickListener = {

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, localDateState.value.year)
            calendar.set(Calendar.DAY_OF_YEAR, localDateState.value.dayOfYear)
            calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
            calendar.set(Calendar.MINUTE, timePickerState.minute)
            val timeMiles = calendar.timeInMillis
            onDoneClickListener(timeMiles)
        },
        content = {
            Column(
                modifier = modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //label alert  12-12-2024  12:34
                Text(
                    modifier = modifier.padding(bottom = 16.dp),
                    text = labelDate.value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Divider()
                Box(
                    modifier = modifier
                        .clickable { isShowTimePicker.value = true }
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = shape
                        ),
                ) {
                    Row(
                        modifier = modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = modifier.weight(1f),
                            text = "${timePickerState.hour}:" + formatMinute(minute = timePickerState.minute.toString())
                        )
                        Image(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                }
                Spacer(modifier = Modifier.heightIn(8.dp))
                Box(
                    modifier = modifier
                        .clickable { isShowDatePicker.value = true }
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = shape
                        )
                ) {
                    Row(
                        modifier = modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = modifier.weight(1f),
                            text = localDateState.value.toString()
                        )
                        Image(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                }
            }
        }

    )

    if (isShowTimePicker.value) {
        PickerAlert(
            onDismissRequest = { isShowTimePicker.value = false },
            content = {
                TimePicker(state = timePickerState)
            })
    }
    if (isShowDatePicker.value) {
        PickerAlert(
            onDismissRequest = { isShowDatePicker.value = false },
            content = {
                MyCalendar(
                    onItemClickListener = {
                        localDateState.value = it
                    }
                )
            }
        )
    }
}


private fun formatMinute(minute: String): String {
    return if (minute.length == 1) "0$minute" else minute
}

