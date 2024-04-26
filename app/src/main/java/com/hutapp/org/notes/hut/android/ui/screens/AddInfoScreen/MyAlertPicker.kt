package com.hutapp.org.notes.hut.android.ui.screens.AddInfoScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hutapp.org.notes.hut.android.ui.screens.calendar_screen.MyCalendar
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAlertPicker(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onDoneClickListener: (Long) -> Unit = {},
) {
    val localDateState = rememberSaveable { mutableStateOf(LocalDate.now(ZoneId.systemDefault())) }

    val localTime = LocalTime.now(ZoneId.systemDefault())
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
