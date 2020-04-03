package com.aas.footballapp.ui.favorite.favoriteteam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aas.footballapp.data.model.Team

class FavoriteTeamViewModel : ViewModel(){
    private var favoriteTeam = MutableLiveData<List<Team>>()

    fun setTeam(team: List<Team>){
        favoriteTeam.postValue(team)
    }

    fun getTeam() : LiveData<List<Team>>{
        return favoriteTeam
    }
}