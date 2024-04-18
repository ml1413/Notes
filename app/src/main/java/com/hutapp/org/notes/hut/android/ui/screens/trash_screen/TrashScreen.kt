package com.hutapp.org.notes.hut.android.ui.screens.trash_screen

import android.util.Log
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
import com.hutapp.org.notes.hut.android.ui.myUiComponent.MyLazyForItemNotes
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.CurrentScreenViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.TabItemList
import com.hutapp.org.notes.hut.android.ui.tabRow.TabRowCurrentItemViewModel

@Composable
fun TrashScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    tabRowCurrentItemViewModel: TabRowCurrentItemViewModel,
    currentScreenViewModel: CurrentScreenViewModel,
    tabItemList: TabItemList,
    noteViewModel: NoteViewModel,
    itemForDeleteViewModel: ItemForDeleteViewModel = viewModel(),
    isShowDeleteInTrashItem: Boolean,
    onItemClickListener: (NoteEntity) -> Unit,
    onFABclickListener: () -> Unit,
    onBackHandler: () -> Unit

) {
    Log.d("TAG1", "TrashScreen: ")
    BackHandler {
        onBackHandler()
    }

    val listEntity = noteViewModel.noteList.observeAsState()
    val alertIsVisible = rememberSaveable { mutableStateOf(false) }

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
                    val noteForRestore = noteEntity.copy(isDelete = false)
                    noteViewModel.updateNote(noteEntity = noteForRestore)
                }
            })
    }

    MyLazyForItemNotes(
        paddingValues = paddingValues,
        noteViewModel = noteViewModel,
        isShowDeleteInTrashItem = isShowDeleteInTrashItem,
        currentScreenViewModel = currentScreenViewModel,
        tabRowCurrentItemViewModel = tabRowCurrentItemViewModel,
        tabItemList = tabItemList,
        index = 0,
        onItemClickListener = {},
        onItemIconClickListener = { noteEntity ->
            itemForDeleteViewModel.setValue(noteEntity = noteEntity)
            alertIsVisible.value = true
        }
    ) {

    }

//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(paddingValues),
//    ) {
//        LazyColumn(
//            modifier = modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            listEntity.value?.let { listEntity ->
//                val filterList = listEntity.filter {
//                    it.isDelete == isShowDeleteInTrashItem
//                }
//                items(filterList) { noteEntity ->
//                    Box(
//                        modifier = modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 16.dp, vertical = 2.dp)
//                            .clickable { onItemClickListener(noteEntity) }
//                            .border(
//                                width = 1.dp,
//                                shape = RoundedCornerShape(8.dp),
//                                color = MaterialTheme.colorScheme.onBackground
//                            )
//                    ) {
//                        Row {
//                            Column(
//                                modifier = modifier
//                                    .weight(1f)
//                                    .padding(horizontal = 16.dp, vertical = 4.dp)
//                            ) {
//                                Text(
//                                    text = noteEntity.labelNote,
//                                    maxLines = 1,
//                                    fontWeight = FontWeight.Bold
//                                )
//                                Text(
//                                    maxLines = 1,
//                                    text = noteEntity.message
//                                )
//                            }
//                            IconButton(onClick = {
//                                itemForDeleteViewModel.setValue(noteEntity = noteEntity)
//                                alertIsVisible.value = true
//                            }) {
//                                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//    Box(
//        modifier = modifier.fillMaxSize(),
//        contentAlignment = Alignment.BottomEnd
//    ) {
//        FloatingActionButton(
//            modifier = modifier.padding(16.dp),
//            onClick = { onFABclickListener() },
//            content = {
//                Icon(imageVector = Icons.Default.Add, contentDescription = null)
//            })
//    }
}
