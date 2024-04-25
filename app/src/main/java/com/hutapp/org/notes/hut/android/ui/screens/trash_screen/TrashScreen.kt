package com.hutapp.org.notes.hut.android.ui.screens.trash_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hutapp.org.notes.hut.android.alert.MyAlert
import com.hutapp.org.notes.hut.android.db.NoteEntity
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.notification.AlarmSchedulerImpl
import com.hutapp.org.notes.hut.android.notification.ModelAlarmItem
import com.hutapp.org.notes.hut.android.ui.myUiComponent.MyLazyForItemNotes
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.CurrentScreenViewModel

@Composable
fun TrashScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    currentScreenViewModel: CurrentScreenViewModel,
    alarmSchedulerImpl: AlarmSchedulerImpl,
    noteViewModel: NoteViewModel,
    itemForDeleteViewModel: ItemForDeleteViewModel = viewModel(),
    isShowDeleteInTrashItem: Boolean,
    onItemClickListener: (NoteEntity) -> Unit,
    onBackHandler: () -> Unit

) {
    BackHandler { onBackHandler() }
    val alertIsVisible = rememberSaveable { mutableStateOf(false) }

    val listEntity = noteViewModel.noteList.observeAsState()
    listEntity.value?.let { oldListEntity ->
        val filterList = oldListEntity.filter {
            it.isDelete == isShowDeleteInTrashItem
        }
        MyLazyForItemNotes(
            paddingValues = paddingValues,
            filterList = filterList,
            currentScreenViewModel = currentScreenViewModel,
            onItemClickListener = onItemClickListener,
            onItemIconClickListener = { noteEntity ->
                itemForDeleteViewModel.setValue(noteEntity = noteEntity)
                alertIsVisible.value = true
            }
        )

        if (alertIsVisible.value) {
            MyAlert(
                onDismiss = { alertIsVisible.value = false },
                onDelete = {
                    itemForDeleteViewModel.currentEntity.value?.let { noteEntity ->
                        noteViewModel.deleteNoteFromDB(noteEntity = noteEntity)
                    }
                },
                onRestore = {
                    itemForDeleteViewModel.currentEntity.value?.let { noteEntity ->
                        // restore note
                        val noteForRestore = noteEntity.copy(isDelete = false)
                        noteViewModel.updateNote(noteEntity = noteForRestore)
                        // restore notification
                        noteForRestore.timeNotification?.let { timeNotification ->
                            val currentTime = System.currentTimeMillis()
                            if (currentTime < timeNotification) {
                                noteForRestore.id?.let { id ->
                                    val item = ModelAlarmItem(
                                        id = id,
                                        time = timeNotification,
                                        message = noteForRestore.labelNote
                                    )
                                    alarmSchedulerImpl.scheduler(item = item)
                                }
                            }
                        }
                    }

                })
        }
    }
}

