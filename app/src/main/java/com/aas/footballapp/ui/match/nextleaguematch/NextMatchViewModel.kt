package com.aas.footballapp.ui.match.nextleaguematch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aas.footballapp.data.model.Event

class NextMatchViewModel: ViewModel(){
    private var listEvent = MutableLiveData<List<Event>>()

    fun setNextMatch(event: List<Event>){
        listEvent.postValue(event)
    }

    fun getNextMatch() : LiveData<List<Event>>{
        return listEvent
    }
}