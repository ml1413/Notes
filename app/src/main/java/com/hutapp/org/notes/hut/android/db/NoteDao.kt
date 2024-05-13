package com.hutapp.org.notes.hut.android.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Query("select*from note_db order by id desc")
    fun getAllNotes(): LiveData<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<NoteEntity>)
}