package com.hutapp.org.notes.my_organization.android.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_db")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val labelNoteScreen: String,
    val labelNote: String,
    val isDelete: Boolean = false,
    val message: String,
    val timeNotification: Long? = null,
    val addNoteDate: String
)