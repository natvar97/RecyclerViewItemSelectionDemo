package com.indialone.recyclerviewitemselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {

    private val listItem = MutableLiveData<String>()

    fun setText(item: String) {
        listItem.postValue(item)
    }

    fun getText(): LiveData<String> {
        return listItem
    }

}