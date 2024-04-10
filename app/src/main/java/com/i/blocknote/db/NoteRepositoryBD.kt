package com.i.blocknote.db

import javax.inject.Inject

class NoteRepositoryBD @Inject constructor(private val noteDataBase: NoteDataBase) {

    fun getAll() = noteDataBase.getDao().getAllNotes()
   suspend fun deleteNote(noteEntity: NoteEntity) {
        noteDataBase.getDao().deleteNote(noteEntity)
    }

    suspend fun add(noteEntity: NoteEntity) {
        noteDataBase.getDao().addNote(noteEntity = noteEntity)
    }
}