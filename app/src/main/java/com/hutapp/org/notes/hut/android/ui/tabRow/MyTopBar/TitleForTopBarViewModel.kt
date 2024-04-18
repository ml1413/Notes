package com.hutapp.org.notes.hut.android.ui.tabRow.MyTopBar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hutapp.org.notes.hut.android.ui.navigation.Screens

class TitleForTopBarViewModel : ViewModel() {
    private val _screen = MutableLiveData<Screens>()
    val screen: LiveData<Screens> = _screen

    fun setTitleScreen(screen: Screens) {
        _screen.value = screen
    }
}