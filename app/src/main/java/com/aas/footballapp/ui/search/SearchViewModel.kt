package com.aas.footballapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aas.footballapp.data.model.Event
import com.aas.footballapp.data.model.Team

class SearchViewModel : ViewModel() {
    private var listEvent = MutableLiveData<List<Event>>()
    private var listTeam = MutableLiveData<List<Team>>()

    fun setEvent(event: List<Event>){
        listEvent.postValue(event)
    }

    fun getEvent() : LiveData<List<Event>>{
        return listEvent
    }

    fun setTeam(team: List<Team>){
        listTeam.postValue(team)
    }

    fun getTeam() : LiveData<List<Team>>{
        return listTeam
    }
}