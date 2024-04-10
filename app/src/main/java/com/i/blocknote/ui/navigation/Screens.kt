package com.i.blocknote.ui.navigation

import com.i.blocknote.db.NoteEntity

sealed class Screens(val route: String) {
    object AllNotesScreen : Screens(route = ROUTE_ALL_NOTES_SCREEN)
    object BookmarksScreen : Screens(route = ROUTE_BOOKMARKS_SCREEN)
    object NoteScreen : Screens(route = ROUTE_NOTE_SCREEN)
    object RemindersScreen : Screens(route = ROUTE_REMINDERS_SCREEN)
    object SettingsScreen : Screens(route = ROUTE_SETTING_SCREEN)
    object TrashScreen : Screens(route = ROUTE_TRASH_SCREEN)
    object AddScreen : Screens(route = ROUTE_ADD_SCREEN)
    object ReadNoteScreen : Screens(route = ROUTE_READ_NOTE_SCREEN) {
        private const val ROUTE_FOR_ARGS = "read note screen"
        fun getRouteWithArgs(noteEntity: NoteEntity): String {
            return ROUTE_FOR_ARGS + "/${noteEntity.id ?: 0}"
        }
    }

    companion object {

        const val KEY_ARG_NOTE_ENTITY_ID = "id"

        private const val ROUTE_ALL_NOTES_SCREEN = "allScreens"
        private const val ROUTE_BOOKMARKS_SCREEN = "bookmarks"
        private const val ROUTE_NOTE_SCREEN = "note screen"
        private const val ROUTE_REMINDERS_SCREEN = "reminders"
        private const val ROUTE_SETTING_SCREEN = "setting"
        private const val ROUTE_TRASH_SCREEN = "trash"
        private const val ROUTE_ADD_SCREEN = "add screen"
        private const val ROUTE_READ_NOTE_SCREEN = "read note screen/{$KEY_ARG_NOTE_ENTITY_ID}"
    }

}