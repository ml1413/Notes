package com.hutapp.org.notes.my_organization.android.ui.tabRow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TabRowCurrentItemViewModel : ViewModel() {
    private val _currentItem = MutableLiveData<ModelTabRowItem>()
    val currentItem: LiveData<ModelTabRowItem> = _currentItem
    fun setItem(modelTabRowItem: ModelTabRowItem) {
        _currentItem.value = modelTabRowItem
    }
}