package com.hutapp.org.notes.hut.android.ui.myUiComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.CurrentScreenViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.TabItemList

@Composable
fun MyLazyForItemNotes(
    modifier: Modifier = Modifier,
    filterList: List<NoteEntity>,
    paddingValues: PaddingValues = PaddingValues(),
    currentScreenViewModel: CurrentScreenViewModel,
    onItemIconClickListener: (NoteEntity) -> Unit = {},
    onItemClickListener: (NoteEntity) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
    ) {
        items(filterList) { noteEntity ->
            MyItemBox(
                onItemClickListener = onItemClickListener,
                noteEntity = noteEntity,
                currentScreenViewModel = currentScreenViewModel,
                onIconButtonClickListener = { onItemIconClickListener(noteEntity) }
            )
        }
    }
}
