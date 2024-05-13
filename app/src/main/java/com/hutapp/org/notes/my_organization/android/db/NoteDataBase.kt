package com.hutapp.org.notes.my_organization.android.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun getDao(): NoteDao
}