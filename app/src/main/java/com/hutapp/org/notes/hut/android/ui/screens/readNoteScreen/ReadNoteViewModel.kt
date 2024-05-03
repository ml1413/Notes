package com.hutapp.org.notes.hut.android.ui.screens.readNoteScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hutapp.org.notes.hut.android.db.NoteEntity

class ReadNoteViewModel : ViewModel() {
    private val _noteEntity = MutableLiveData<NoteEntity>()
    val noteEntity: LiveData<NoteEntity> = _noteEntity
    fun setValue(noteEntity: NoteEntity) {
        _noteEntity.value = noteEntity
    }
}