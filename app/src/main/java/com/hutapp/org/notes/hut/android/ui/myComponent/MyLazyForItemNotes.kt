package com.hutapp.org.notes.hut.android.ui.myComponent

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.CurrentScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MyLazyForItemNotes(
    modifier: Modifier = Modifier,
    idEntity2: (Boolean) -> Int? = { null },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    filterList: List<NoteEntity>,
    paddingValues: PaddingValues = PaddingValues(),
    currentScreenViewModel: CurrentScreenViewModel,
    onItemIconClickListener: (NoteEntity) -> Unit = {},
    onItemClickListener: (NoteEntity) -> Unit,
) {
    val scrollState = rememberLazyListState()
    val interactionSource = remember { MutableInteractionSource() }

    LazyColumn(
        state = scrollState,
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
    ) {
        items(filterList) { noteEntity ->
            // show item from notification__________________________________________________________
            idEntity2(true)?.let { id ->
                Log.d("TAG1", "MyLazyForItemNotes: id $id  noteEntity ${noteEntity.id} ")
                if (noteEntity.id == id) {
                    coroutineScope.launch {
                        delay(600)
                        scrollState.animateScrollToItem(filterList.indexOf(noteEntity))
                        val press = PressInteraction.Press(Offset.Zero)
                        interactionSource.emit(press)
                        delay(500)
                        interactionSource.emit(PressInteraction.Release(press))
                        delay(500)
                        onItemClickListener(noteEntity)
                    }
                }
            }
            //______________________________________________________________________________________
            MyItemBox(
                onItemClickListener = onItemClickListener,
                interactionSource = interactionSource,
                idEntity2 = idEntity2,
                noteEntity = noteEntity,
                currentScreenViewModel = currentScreenViewModel,
                onIconButtonClickListener = { onItemIconClickListener(noteEntity) }
            )
        }
    }
}
