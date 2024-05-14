package com.hutapp.org.notes.my_organization.android.ui.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.hutapp.org.notes.my_organization.android.db.NoteViewModel
import com.hutapp.org.notes.my_organization.android.notification.AlarmSchedulerImpl
import com.hutapp.org.notes.my_organization.android.ui.drawerSheet.DrawerItemStateViewModel
import com.hutapp.org.notes.my_organization.android.ui.drawerSheet.MyDrawerSheet
import com.hutapp.org.notes.my_organization.android.ui.screens.AddInfoScreen.AddInfoScreen
import com.hutapp.org.notes.my_organization.android.ui.screens.BackUpScreen
import com.hutapp.org.notes.my_organization.android.ui.screens.NoteLazyScreen
import com.hutapp.org.notes.my_organization.android.ui.screens.SettingsScreen
import com.hutapp.org.notes.my_organization.android.ui.screens.calendar_screen.CalendarScreen
import com.hutapp.org.notes.my_organization.android.ui.screens.readNoteScreen.ReadNoteScreen
import com.hutapp.org.notes.my_organization.android.ui.screens.readNoteScreen.ReadNoteViewModel
import com.hutapp.org.notes.my_organization.android.ui.screens.trash_screen.TrashScreen
import com.hutapp.org.notes.my_organization.android.ui.tabRow.MyTabRowScreen
import com.hutapp.org.notes.my_organization.android.ui.tabRow.MyTopBar.CurrentScreenViewModel
import com.hutapp.org.notes.my_organization.android.ui.tabRow.MyTopBar.MyTopBar
import com.hutapp.org.notes.my_organization.android.ui.tabRow.TabItemList
import com.hutapp.org.notes.my_organization.android.ui.tabRow.TabRowCurrentItemViewModel
import com.hutapp.org.notes.my_organization.android.utilsAccount.AccountViewModel
import com.hutapp.org.notes.my_organization.android.utilsAccount.MyGoogleDriveHelper

@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
    idEntity2: (Boolean) -> Int?,
    context: Context,
    launchPermissionNotification: PermissionState,
    noteViewModel: NoteViewModel,
    myGoogleDriveHelper: MyGoogleDriveHelper,
    tabItemList: TabItemList,
    alarmSchedulerImpl: AlarmSchedulerImpl,
    tabRowCurrentItemViewModel: TabRowCurrentItemViewModel,
    drawerItemStateViewModel: DrawerItemStateViewModel,
    currentScreenViewModel: CurrentScreenViewModel,
) {
    val readNoteViewModel: ReadNoteViewModel = viewModel()
    val navHostController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val accountVewModel: AccountViewModel = viewModel()



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MyDrawerSheet(
                modifier = modifier,
                drawerItemStateViewModel = drawerItemStateViewModel,
                coroutineScope = coroutineScope,
                accountVewModel = accountVewModel,
                myGoogleDriveHelper = myGoogleDriveHelper,
                drawerState = drawerState,
                onItemDrawMenuListener = { screen ->
                    currentScreenViewModel.setScreen(screen = screen)
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
                            currentScreenViewModel.setScreen(screen = Screens.AllNotesScreen)
                            navHostController.popBackStack()
                        },
                        onCalendarClickListener = {
                            currentScreenViewModel.setScreen(screen = Screens.CalendarScreen)
                            navHostController.navigate(Screens.CalendarScreen.route) {
                                popUpTo(Screens.AllNotesScreen.route)
                                launchSingleTop = true
                            }
                        },
                        onSharedClickListener = {
                            readNoteViewModel.noteEntity.value?.apply {
                                val intent = Intent(Intent.ACTION_SEND)
                                intent.setType("text/plain")
                                intent.putExtra(Intent.EXTRA_TEXT, message)
                                context.startActivity(intent)
                            }
                        }
                    )
                },
                content = { paddingValues ->
                    AppNavGraph(
                        navHostController = navHostController,
                        allNotesScreensContent = {
                            currentScreenViewModel.setScreen(screen = Screens.AllNotesScreen)
                            MyTabRowScreen(
                                paddingValues = paddingValues,
                                idEntity2 = idEntity2,
                                coroutineScope = coroutineScope,
                                tabItemList = tabItemList,
                                onCurrentPageListener = { currentPage ->
                                    tabRowCurrentItemViewModel.setItem(tabItemList.listItem[currentPage])
                                },
                                pageContent = { index ->

                                    NoteLazyScreen(
                                        index = index,
                                        tabItemList = tabItemList,
                                        coroutineScope = coroutineScope,
                                        idEntity2 = idEntity2,
                                        currentScreenViewModel = currentScreenViewModel,
                                        noteViewModel = noteViewModel,
                                        isShowDeleteInTrashItem = false,
                                        onItemClickListener = { noteEntity ->
                                            readNoteViewModel.setValue(noteEntity = noteEntity)
                                            currentScreenViewModel.setScreen(Screens.ReadNoteScreen)
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
                                    currentScreenViewModel.setScreen(screen = Screens.AllNotesScreen)
                                    navHostController.popBackStack()
                                }
                            )
                        },
                        addScreenContent = {
                            AddInfoScreen(
                                tabRowCurrentItemViewModel = tabRowCurrentItemViewModel,
                                launchPermissionNotification = launchPermissionNotification,
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
                                readNoteViewModel = readNoteViewModel,
                                noteViewModel = noteViewModel,
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
                                    currentScreenViewModel.setScreen(screen = Screens.AllNotesScreen)
                                    navHostController.popBackStack()
                                }
                            )
                        },
                        backupContent = {
                            BackUpScreen(
                                paddingValues = paddingValues,
                                accountVewModel = accountVewModel,
                                myGoogleDriveHelper = myGoogleDriveHelper,
                                alarmSchedulerImpl = alarmSchedulerImpl,
                                noteViewModel = noteViewModel,
                                onSaveClickListener = {

                                }
                            )
                        })
                }
            )

        }
    )

}
