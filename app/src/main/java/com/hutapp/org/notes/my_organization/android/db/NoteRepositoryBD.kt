package com.hutapp.org.notes.my_organization.android.db

import javax.inject.Inject

class NoteRepositoryBD @Inject constructor(private val noteDataBase: NoteDataBase) {
    private val noteDao = noteDataBase.getDao()

    fun getAll() = noteDao.getAllNotes()
    suspend fun updateEntity(noteEntity: NoteEntity) {
        noteDao.updateNote(noteEntity = noteEntity)
    }

    suspend fun deleteNote(noteEntity: NoteEntity) {
        noteDao.deleteNote(noteEntity)
    }

    suspend fun add(noteEntity: NoteEntity) {
        noteDao.addNote(noteEntity = noteEntity)
    }

    suspend fun insertList(list: List<NoteEntity>) {
        noteDao.insertList(list = list)
    }
}