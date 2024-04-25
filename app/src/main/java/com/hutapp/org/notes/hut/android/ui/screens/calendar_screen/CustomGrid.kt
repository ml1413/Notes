package com.hutapp.org.notes.hut.android.ui.screens.calendar_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import java.time.LocalDate

@Composable
fun CustomGrid(
    modifier: Modifier = Modifier,
    listEntity: State<List<NoteEntity>?>,
    daysInMonthList: List<LocalDate?> = emptyList(),
    onItemClickListener: (LocalDate) -> Unit = {}
) {
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
                        Box(
                            modifier = modifier
                                .weight(1f)
                                .clickable {
                                    currentLocalDate?.let { onItemClickListener(it) }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            currentLocalDate?.let { localDate ->
                                Column(
                                    modifier = modifier.padding(vertical = 12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = localDate.dayOfMonth.toString())
                                    val list =
                                        listEntity.value?.filter { !it.isDelete }
                                            ?.map { it.localDate.toString() }
                                    list?.let {
                                        if (it.contains(localDate.toString()))
                                            Divider(
                                                modifier = modifier.padding(4.dp),
                                                thickness = 2.dp,
                                                color = MaterialTheme.colorScheme.primary
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


