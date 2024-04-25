package com.hutapp.org.notes.hut.android.ui.navigation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.compose.rememberNavController
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.notification.AlarmSchedulerImpl
import com.hutapp.org.notes.hut.android.ui.drawerSheet.DrawerItemStateViewModel
import com.hutapp.org.notes.hut.android.ui.drawerSheet.MyDrawerSheet
import com.hutapp.org.notes.hut.android.ui.screens.AddInfoScreen.AddInfoScreen
import com.hutapp.org.notes.hut.android.ui.screens.NoteLazyScreen
import com.hutapp.org.notes.hut.android.ui.screens.ReadNoteScreen
import com.hutapp.org.notes.hut.android.ui.screens.SettingsScreen
import com.hutapp.org.notes.hut.android.ui.screens.calendar_screen.CalendarScreen
import com.hutapp.org.notes.hut.android.ui.screens.trash_screen.TrashScreen
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTabRowScreen
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.CurrentScreenViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.MyTopBar
import com.hutapp.org.notes.hut.android.ui.tabRow.TabItemList
import com.hutapp.org.notes.hut.android.ui.tabRow.TabRowCurrentItemViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
    noteViewModel: NoteViewModel,
    tabItemList: TabItemList,
    alarmSchedulerImpl: AlarmSchedulerImpl,
    tabRowCurrentItemViewModel: TabRowCurrentItemViewModel,
    drawerItemStateViewModel: DrawerItemStateViewModel,
    currentScreenViewModel: CurrentScreenViewModel,
) {
    val navHostController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MyDrawerSheet(
                modifier = modifier,
                drawerItemStateViewModel = drawerItemStateViewModel,
                coroutineScope = coroutineScope,
                drawerState = drawerState,
                onItemDrawMenuListener = { screen ->
                    currentScreenViewModel.setTitleScreen(screen = screen)
                    navHostController.navigate(screen.route) {
                        popUpTo(Screens.AllNotesScreen.route)
                        launchSingleTop = true
                    }
                }
            )
        }, content = {
            Scaffold(
                topBar = {
                    MyTopBar(
                        coroutineScope = coroutineScope,
                        drawerState = drawerState,
                        currentScreenViewModel = currentScreenViewModel,
                        onBackButtonClickListener = {
                            currentScreenViewModel.setTitleScreen(screen = Screens.AllNotesScreen)
                            navHostController.popBackStack()
                        }, onCalendarClickListener = {
                            currentScreenViewModel.setTitleScreen(screen = Screens.CalendarScreen)
                            navHostController.navigate(Screens.CalendarScreen.route) {
                                popUpTo(Screens.AllNotesScreen.route)
                                launchSingleTop = true
                            }
                        }
                    )
                },
                content = { paddingValues ->
                    AppNavGraph(
                        navHostController = navHostController,
                        allNotesScreensContent = {
                            currentScreenViewModel.setTitleScreen(screen = Screens.AllNotesScreen)
                            MyTabRowScreen(
                                paddingValues = paddingValues,
                                coroutineScope = coroutineScope,
                                tabItemList = tabItemList,
                                onCurrentPageListener = { currentPage ->
                                    tabRowCurrentItemViewModel.setItem(tabItemList.listItem[currentPage])
                                },
                                pageContent = { index ->

                                    NoteLazyScreen(
                                        index = index,
                                        tabItemList = tabItemList,
                                        currentScreenViewModel = currentScreenViewModel,
                                        noteViewModel = noteViewModel,
                                        isShowDeleteInTrashItem = false,
                                        onItemClickListener = { noteEntity ->
                                            navHostController.navigate(
                                                Screens.ReadNoteScreen.getRouteWithArgs(
                                                    noteEntity = noteEntity
                                                )
                                            )
                                        })

                                },
                                onFABClickListener = {
                                    navHostController.navigate(Screens.AddScreen.route)
                                }
                            )
                        },
                        settingScreenContent = {
                            // todo need Override BackHandler
                            SettingsScreen(
                                paddingValues = paddingValues
                            )
                        },
                        trashScreenContent = {
                            TrashScreen(
                                paddingValues = paddingValues,
                                currentScreenViewModel = currentScreenViewModel,
                                alarmSchedulerImpl = alarmSchedulerImpl,
                                noteViewModel = noteViewModel,
                                isShowDeleteInTrashItem = true,
                                onItemClickListener = {},
                                onBackHandler = {
                                    currentScreenViewModel.setTitleScreen(screen = Screens.AllNotesScreen)
                                    navHostController.popBackStack()
                                }
                            )
                        },
                        addScreenContent = {
                            AddInfoScreen(
                                tabRowCurrentItemViewModel = tabRowCurrentItemViewModel,
                                alarmSchedulerImpl = alarmSchedulerImpl,
                                paddingValues = paddingValues,
                                noteViewModel = noteViewModel,
                                onFABclickListener = {
                                    navHostController.popBackStack()
                                }
                            )
                        },
                        readScreenContent = { noteEntityId ->
                            ReadNoteScreen(
                                paddingValues = paddingValues,
                                alarmSchedulerImpl = alarmSchedulerImpl,
                                noteViewModel = noteViewModel,
                                noteEntityId = noteEntityId,
                                onFABClickListener = {
                                    navHostController.popBackStack()
                                }
                            )
                        },
                        calendarScreen = {
                            CalendarScreen(
                                paddingValues = paddingValues,
                                noteViewModel = noteViewModel,
                                currentScreenViewModel = currentScreenViewModel,
                                onItemClickListener = { noteEntity ->
                                    navHostController.navigate(
                                        Screens.ReadNoteScreen.getRouteWithArgs(
                                            noteEntity = noteEntity
                                        )
                                    )
                                },
                                onBackListener = {
                                    currentScreenViewModel.setTitleScreen(screen = Screens.AllNotesScreen)
                                    navHostController.popBackStack()
                                }
                            )
                        })
                }
            )

        }
    )

}
