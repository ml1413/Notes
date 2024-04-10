package com.i.blocknote.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.i.blocknote.db.NoteViewModel
import com.i.blocknote.ui.drawerSheet.DrawerItemStateViewModel
import com.i.blocknote.ui.drawerSheet.MyDrawerSheet
import com.i.blocknote.ui.screens.AddInfoScreen
import com.i.blocknote.ui.screens.BookmarksScreen
import com.i.blocknote.ui.screens.NoteScreen
import com.i.blocknote.ui.screens.ReadNoteScreen
import com.i.blocknote.ui.screens.RemindersScreen
import com.i.blocknote.ui.screens.SettingsScreen
import com.i.blocknote.ui.screens.TrashScreen
import com.i.blocknote.ui.tabRow.MyTabRowScreen
import com.i.blocknote.ui.tabRow.TabRowStateViewModel
import com.i.blocknote.ui.theme.MyTopBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
    noteViewModel: NoteViewModel,
    tabRowStateViewModel: TabRowStateViewModel,
    drawerItemStateViewModel: DrawerItemStateViewModel
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
                coroutineScope,
                drawerState,
                onItemDrawMenuListener = { screen ->
                    navHostController.navigate(screen.route)
                }
            )
        }, content = {
            Scaffold(
                topBar = {
                    MyTopBar(
                        drawerItemStateViewModel = drawerItemStateViewModel,
                        coroutineScope = coroutineScope,
                        drawerState = drawerState,
                    )
                },
                content = { paddingValues ->
                    AppNavGraph(
                        navHostController = navHostController,
                        allNotesScreensContent = {
                            drawerItemStateViewModel.selectedItemDrawState.value =
                                drawerItemStateViewModel.listMenu.first { it.screens == Screens.AllNotesScreen }
                            MyTabRowScreen(
                                paddingValues = paddingValues,
                                tabRowStateViewModel = tabRowStateViewModel,
                                pageContent = { index ->
                                    when (index) {
                                        0 -> NoteScreen(noteViewModel = noteViewModel,
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

                                        1 -> RemindersScreen(paddingValues = paddingValues)
                                        2 -> BookmarksScreen(paddingValues = paddingValues)
                                    }

                                }
                            )
                        },
                        settingScreenContent = {
                            drawerItemStateViewModel.selectedItemDrawState.value =
                                drawerItemStateViewModel.listMenu.first { it.screens == Screens.SettingsScreen }
                            SettingsScreen(
                                paddingValues = paddingValues
                            )
                        },
                        trashScreenContent = {
                            drawerItemStateViewModel.selectedItemDrawState.value =
                                drawerItemStateViewModel.listMenu.first { it.screens == Screens.TrashScreen }
                            TrashScreen(paddingValues = paddingValues)
                        },
                        addScreenContent = {
                            AddInfoScreen(
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
                        })
                }
            )

        }
    )

}