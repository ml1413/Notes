package com.hutapp.org.notes.hut.android.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hutapp.org.notes.hut.android.db.NoteViewModel
import com.hutapp.org.notes.hut.android.ui.drawerSheet.DrawerItemStateViewModel
import com.hutapp.org.notes.hut.android.ui.drawerSheet.MyDrawerSheet
import com.hutapp.org.notes.hut.android.ui.screens.AddInfoScreen
import com.hutapp.org.notes.hut.android.ui.screens.NoteLazyScreen
import com.hutapp.org.notes.hut.android.ui.screens.ReadNoteScreen
import com.hutapp.org.notes.hut.android.ui.screens.SettingsScreen
import com.hutapp.org.notes.hut.android.ui.screens.calendar_screen.CalendarScreen
import com.hutapp.org.notes.hut.android.ui.screens.trash_screen.TrashScreen
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTabRowScreen
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.MyTopBar
import com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar.TitleForTopBarViewModel
import com.hutapp.org.notes.hut.android.ui.tabRow.TabItemList
import com.hutapp.org.notes.hut.android.ui.tabRow.TabRowCurrentItemViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
    noteViewModel: NoteViewModel,
    tabItemList: TabItemList,
    tabRowCurrentItemViewModel: TabRowCurrentItemViewModel,
    drawerItemStateViewModel: DrawerItemStateViewModel,
    titleForTopBarViewModel: TitleForTopBarViewModel,
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
                    titleForTopBarViewModel.setTitleScreen(screen = screen)
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
                        titleForTopBarViewModel = titleForTopBarViewModel,
                        onBackButtonClickListener = {
                            titleForTopBarViewModel.setTitleScreen(screen = Screens.AllNotesScreen)
                            navHostController.popBackStack()
                        }, onCalendarClickListener = {
                            titleForTopBarViewModel.setTitleScreen(screen = Screens.CalendarScreen)
                            navHostController.navigate(Screens.CalendarScreen.route){
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
                            titleForTopBarViewModel.setTitleScreen(screen = Screens.AllNotesScreen)
                            MyTabRowScreen(
                                paddingValues = paddingValues,
                                coroutineScope = coroutineScope,
                                tabItemList = tabItemList,
                                pageContent = { index ->
                                    NoteLazyScreen(
                                        index = index,
                                        tabItemList = tabItemList,
                                        tabRowCurrentItemViewModel = tabRowCurrentItemViewModel,
                                        noteViewModel = noteViewModel,
                                        isShowDeleteInTrashItem = false,
                                        onFABclickListener = {
                                            navHostController.navigate(Screens.AddScreen.route)
                                        },
                                        onItemClickListener = { noteEntity ->
                                            navHostController.navigate(
                                                Screens.ReadNoteScreen.getRouteWithArgs(
                                                    noteEntity = noteEntity
                                                )
                                            )
                                        })

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
                                tabRowCurrentItemViewModel = tabRowCurrentItemViewModel,
                                noteViewModel = noteViewModel,
                                isShowDeleteInTrashItem = true,
                                onFABclickListener = {},
                                onItemClickListener = {},
                                onBackHandler = {
                                    titleForTopBarViewModel.setTitleScreen(screen = Screens.AllNotesScreen)
                                    navHostController.popBackStack()
                                }
                            )
                        },
                        addScreenContent = {
                            AddInfoScreen(
                                tabRowCurrentItemViewModel = tabRowCurrentItemViewModel,
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
                                onBackListener = {
                                    titleForTopBarViewModel.setTitleScreen(screen = Screens.AllNotesScreen)
                                    navHostController.popBackStack()
                                }
                            )
                        })
                }
            )

        }
    )

}
