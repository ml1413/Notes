package com.hutapp.org.notes.hut.android.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repositoryBD: NoteRepositoryBD
) : ViewModel() {
    private val _noteList = MutableLiveData<List<NoteEntity>>()
    val noteList: LiveData<List<NoteEntity>> = _noteList
    private val observer = Observer<List<NoteEntity>> { listNoteEntity ->
        _noteList.postValue(listNoteEntity)
    }

    init {
        updateCurrentViewModel()
    }

    fun updateCurrentViewModel() {
        repositoryBD.getAll().observeForever(observer)
    }

    fun deleteNoteFromDB(noteEntity: NoteEntity) {
        viewModelScope.launch {
            repositoryBD.deleteNote(noteEntity = noteEntity)
        }
    }

    fun updateNote(noteEntity: NoteEntity) {
        viewModelScope.launch {
            repositoryBD.updateEntity(noteEntity)
        }
    }

    fun addNoteEntityInDB(noteEntity: NoteEntity) {
        viewModelScope.launch {
            repositoryBD.add(noteEntity = noteEntity)
        }
    }

    override fun onCleared() {
        repositoryBD.getAll().removeObserver(observer)
        super.onCleared()
    }
}