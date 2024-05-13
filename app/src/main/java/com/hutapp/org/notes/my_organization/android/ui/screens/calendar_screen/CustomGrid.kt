package com.hutapp.org.notes.my_organization.android.ui.screens.calendar_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.my_organization.android.R
import com.hutapp.org.notes.my_organization.android.db.NoteEntity
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun CustomGrid(
    modifier: Modifier = Modifier,
    listEntity: State<List<NoteEntity>?>,
    daysInMonthList: List<LocalDate?> = emptyList(),
    onItemClickListener: (LocalDate) -> Unit = {}
) {
    val todayLocalDate = LocalDate.now(ZoneId.systemDefault())
    //________________
    val listDay = listOf(
        stringResource(R.string.mon),
        stringResource(R.string.tue),
        stringResource(R.string.wed),
        stringResource(R.string.thu),
        stringResource(R.string.fri),
        stringResource(R.string.sat),
        stringResource(R.string.sun)
    )
    //_______________
    Box(
        modifier = modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        val selectedDate = remember {
            mutableStateOf(0)
        }
        var count = 0
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            //______________________________________________________________
            Row {
                repeat(7) {
                    Box(
                        modifier = modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = listDay[it],
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            //______________________________________________________________
            repeat(7) {
                Row(modifier = modifier.fillMaxWidth()) {
                    repeat(7) {
                        val currentLocalDate = daysInMonthList[count]
                        val shape = RoundedCornerShape(8.dp)
                        val color =
                            if (currentLocalDate != null && currentLocalDate == todayLocalDate) {
                                MaterialTheme.colorScheme.inversePrimary
                            } else {
                                Color.Unspecified
                            }
                        Box(
                            modifier = modifier
                                .clip(shape = shape)
                                .border(
                                    width = 1.dp,
                                    color =
                                    if (selectedDate.value == currentLocalDate?.dayOfMonth) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        Color.Unspecified
                                    },
                                    shape = shape
                                )
                                .background(color)
                                .weight(1f)
                                .clickable {

                                    currentLocalDate?.let { localDate ->
                                        selectedDate.value = localDate.dayOfMonth
                                        onItemClickListener(localDate)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            currentLocalDate?.let { localDate ->
                                Column(
                                    modifier = modifier.padding(vertical = 12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = localDate.dayOfMonth.toString()
                                    )
                                    val list =
                                        listEntity.value?.filter { !it.isDelete }
                                            ?.map { it.addNoteDate.toString() }
                                    list?.let {

                                        Divider(
                                            modifier = modifier.padding(horizontal = 12.dp),
                                            thickness = 2.dp,
                                            color =
                                            if (it.contains(localDate.toString())) {
                                                MaterialTheme.colorScheme.primary
                                            } else {
                                                Color.Unspecified
                                            }
                                        )


                                    }
                                }
                            }

                        }
                        if (count < daysInMonthList.lastIndex) {
                            count += 1
                        }
                    }
                }
            }
        }

    }
}
