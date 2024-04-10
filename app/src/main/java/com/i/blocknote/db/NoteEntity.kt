package com.i.blocknote.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_db")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val label: String,
    val message: String
)