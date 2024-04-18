package com.hutapp.org.notes.hut.android.ui.navigation

import com.hutapp.org.notes.hut.android.R
import com.hutapp.org.notes.hut.android.db.NoteEntity

sealed class Screens(val title: Int, val route: String) {
    object Initial : Screens(title = R.string.initial, route = ROUTE_INITIAL)
    object AllNotesScreen : Screens(title = R.string.all_notes, route = ROUTE_ALL_NOTES_SCREEN)
    object SettingsScreen : Screens(title = R.string.setting, route = ROUTE_SETTING_SCREEN)
    object TrashScreen : Screens(title = R.string.trashh, route = ROUTE_TRASH_SCREEN)
    object AddScreen : Screens(title = R.string.add_notes, route = ROUTE_ADD_SCREEN)
    object CalendarScreen : Screens(title = R.string.calendar, route = ROUTE_CALENDAR_SCREEN)
    object ReadNoteScreen : Screens(title = R.string.read_notes, route = ROUTE_READ_NOTE_SCREEN) {
        private const val ROUTE_FOR_ARGS = "read note screen"
        fun getRouteWithArgs(noteEntity: NoteEntity): String {
            return ROUTE_FOR_ARGS + "/${noteEntity.id ?: 0}"
        }
    }

    companion object {

        const val KEY_ARG_NOTE_ENTITY_ID = "id"

        private const val ROUTE_INITIAL = "initial"
        private const val ROUTE_ALL_NOTES_SCREEN = "allScreens"
        private const val ROUTE_SETTING_SCREEN = "setting"
        private const val ROUTE_TRASH_SCREEN = "trash"
        private const val ROUTE_ADD_SCREEN = "add screen"
        private const val ROUTE_CALENDAR_SCREEN = "calendar"
        private const val ROUTE_READ_NOTE_SCREEN = "read note screen/{$KEY_ARG_NOTE_ENTITY_ID}"
    }

}