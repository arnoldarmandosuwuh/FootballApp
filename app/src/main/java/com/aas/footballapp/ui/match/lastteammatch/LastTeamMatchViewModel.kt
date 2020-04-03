package com.aas.footballapp.ui.match.lastteammatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aas.footballapp.data.model.Event

class LastTeamMatchViewModel : ViewModel() {
    private var listEvent = MutableLiveData<List<Event>>()

    fun setLastMatch(event: List<Event>) {
        listEvent.postValue(event)
    }

    fun getLastMatch(): LiveData<List<Event>> {
        return listEvent
    }
}