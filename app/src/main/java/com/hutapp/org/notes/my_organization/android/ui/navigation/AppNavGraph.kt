package com.hutapp.org.notes.my_organization.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    allNotesScreensContent: @Composable () -> Unit,
    settingScreenContent: @Composable () -> Unit,
    trashScreenContent: @Composable () -> Unit,
    addScreenContent: @Composable () -> Unit,
    readScreenContent: @Composable (Int) -> Unit,
    calendarScreen:@Composable ()->Unit,
    backupContent:@Composable ()->Unit,
) {
    NavHost(navController = navHostController,
        startDestination = Screens.AllNotesScreen.route,
        builder = {
            composable(Screens.AllNotesScreen.route) { allNotesScreensContent() }
            composable(Screens.SettingsScreen.route) { settingScreenContent() }
            composable(Screens.TrashScreen.route) { trashScreenContent() }
            composable(Screens.AddScreen.route) { addScreenContent() }
            composable(Screens.CalendarScreen.route){calendarScreen()}
            composable(Screens.BackUpScreen.route){backupContent()}
            //______________________________________________________________________________________
            composable(
                route = Screens.ReadNoteScreen.route,
                arguments = listOf(
                    navArgument(name = Screens.KEY_ARG_NOTE_ENTITY_ID,
                        builder = {
                            type = NavType.IntType
                        })
                )
            ) { navBEntry ->
                val noteEntityId = navBEntry.arguments?.getInt(Screens.KEY_ARG_NOTE_ENTITY_ID) ?: 0
                readScreenContent(noteEntityId)
            }
        })
    //______________________________________________________________________________________

}