package com.hutapp.org.notes.hut.android.ui.screens.trash_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hutapp.org.notes.hut.android.db.NoteEntity

class ItemForDeleteViewModel : ViewModel() {
    private val _currentEntity = MutableLiveData<NoteEntity>()
    val currentEntity: LiveData<NoteEntity> = _currentEntity
    fun setValue(noteEntity: NoteEntity) {
        _currentEntity.value = noteEntity
    }
}