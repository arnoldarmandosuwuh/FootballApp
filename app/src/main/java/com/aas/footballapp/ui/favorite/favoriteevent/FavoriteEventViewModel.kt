package com.aas.footballapp.ui.favorite.favoriteevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aas.footballapp.data.model.Event

class FavoriteEventViewModel : ViewModel(){
    private var favoriteEvent = MutableLiveData<List<Event>>()

    fun setEvent(event: List<Event>){
        favoriteEvent.postValue(event)
    }

    fun getEvent() : LiveData<List<Event>>{
        return favoriteEvent
    }
}