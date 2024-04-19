package com.hutapp.org.notes.hut.android.ui.screens.calendar_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun MyCalendar(
    modifier: Modifier = Modifier,
    listEntity: State<List<NoteEntity>?>,
    noteViewModel: NoteViewModel,
    onItemClickListener: (LocalDate) -> Unit
) {
    val localDate = rememberSaveable { mutableStateOf(LocalDate.now()) }
    val daysInMonthList = getDaysMontList(localDate = localDate.value)
    Column(
        modifier = modifier
            .wrapContentSize()
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { localDate.value = localDate.value.minusMonths(1) }) {
                Icon(
                    imageVector
                    = Icons.Default.ArrowBack, contentDescription = null
                )
            }
            val montYearsText = DateTimeFormatter.ofPattern("MMMM yyyy")
            Text(text = montYearsText.format(localDate.value))
            IconButton(onClick = { localDate.value = localDate.value.plusMonths(1) }) {
                Icon(
                    imageVector
                    = Icons.Default.ArrowForward, contentDescription = null
                )
            }
        }
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
        CustomGrid(
            listDay = listDay,
            listEntity=listEntity,
            daysInMonthList = daysInMonthList,
            noteViewModel = noteViewModel,
            onItemClickListener = onItemClickListener
        )
    }
}

@Composable
private fun getDaysMontList(localDate: LocalDate): MutableList<LocalDate?> {
    val yearsMonth = YearMonth.from(localDate)
    val daysInMonth = yearsMonth.lengthOfMonth()
    val firstOfMonth = localDate.withDayOfMonth(1)
    val dayOfWeek = firstOfMonth.dayOfWeek.value
    val daysInMonthArray = mutableListOf<LocalDate?>()
    for (i in 2..43) {
        if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
            daysInMonthArray.add(null)
        } else {
            daysInMonthArray.add(localDate.withDayOfMonth((i - dayOfWeek)))
        }

    }
    return daysInMonthArray
}