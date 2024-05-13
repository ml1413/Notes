package com.hutapp.org.notes.my_organization.android.ui.screens.AddInfoScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hutapp.org.notes.my_organization.android.R
import com.hutapp.org.notes.my_organization.android.ui.screens.calendar_screen.MyCalendar
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = true, showBackground = true)
fun MyAlertPicker(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onDoneClickListener: (Long) -> Unit = {},
) {
    // error if date not correct __________________________________________________________________
    val isError = remember { mutableStateOf(false) }
    val localDateState = rememberSaveable { mutableStateOf(LocalDate.now(ZoneId.systemDefault())) }

    val localTime = LocalTime.now(ZoneId.systemDefault())
    val timePickerState = rememberTimePickerState(
        initialHour = localTime.hour,
        initialMinute = localTime.minute
    )
    LaunchedEffect(
        key1 = timePickerState.minute,
        key2 = timePickerState.hour,
        key3 = localDateState.value
    ) {
        isError.value = System.currentTimeMillis() + 1000 >= getMilesFromDate(
            year = localDateState.value.year,
            dayOfYear = localDateState.value.dayOfYear,
            hourOfDay = timePickerState.hour,
            minute = timePickerState.minute
        )
    }

    // set current date in label _____________________________________________________________
    val labelDate = remember { mutableStateOf("") }
    SideEffect {
        labelDate.value =
            "${localDateState.value} ${timePickerState.hour}:" + formatMinute(minute = timePickerState.minute.toString())
    }
    // show alert_______________________________________________________________________________
    val isShowTimePicker = remember { mutableStateOf(false) }
    val isShowDatePicker = remember { mutableStateOf(false) }
    //___________________________________________________________________________________________
    PickerAlert(
        isError = isError,
        onDismissRequest = onDismissRequest,
        onDoneClickListener = {
            val timeInMillis = getMilesFromDate(
                year = localDateState.value.year,
                dayOfYear = localDateState.value.dayOfYear,
                hourOfDay = timePickerState.hour,
                minute = timePickerState.minute
            )
            onDoneClickListener(timeInMillis)

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
                OutlinedTextField(
                    interactionSource = remember {
                        MutableInteractionSource()
                    }.also { interactionSource ->
                        LaunchedEffect(key1 = interactionSource) {
                            interactionSource.interactions.collect { interaction ->
                                if (interaction is PressInteraction.Release) {
                                    isShowTimePicker.value = true
                                }
                            }
                        }
                    },
                    value = "${timePickerState.hour}:" + formatMinute(minute = timePickerState.minute.toString()),
                    readOnly = true,
                    label = {
                        Text(text = stringResource(R.string.label_time))
                    },
                    suffix = {
                        Image(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    },
                    isError = isError.value,
                    onValueChange = {})

                Spacer(modifier = Modifier.heightIn(8.dp))
                OutlinedTextField(
                    interactionSource = remember {
                        MutableInteractionSource()
                    }.also { interactionSource ->
                        LaunchedEffect(key1 = interactionSource) {
                            interactionSource.interactions.collect { interaction ->
                                if (interaction is PressInteraction.Release) {
                                    isShowDatePicker.value = true
                                }
                            }
                        }
                    },
                    value = localDateState.value.toString(),
                    onValueChange = { },
                    readOnly = true,
                    label = {
                        Text(text = stringResource(R.string.label_date))
                    },
                    isError = isError.value,
                    suffix = {
                        Image(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                )
            }
        }

    )

    if (isShowTimePicker.value) {
        PickerAlert(
            isError = isError,
            onDismissRequest = { isShowTimePicker.value = false },
            content = {
                TimePicker(state = timePickerState)
            })
    }
    if (isShowDatePicker.value) {
        PickerAlert(
            isError = isError,
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

private fun getMilesFromDate(year: Int, dayOfYear: Int, hourOfDay: Int, minute: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.DAY_OF_YEAR, dayOfYear)
    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
    calendar.set(Calendar.MINUTE, minute)
    return calendar.timeInMillis
}
