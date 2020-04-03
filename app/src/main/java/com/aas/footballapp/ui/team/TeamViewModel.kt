package com.aas.footballapp.ui.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aas.footballapp.data.model.Team

class TeamViewModel: ViewModel(){
    private var listTeam = MutableLiveData<List<Team>>()

    fun setTeam(team: List<Team>){
        listTeam.postValue(team)
    }

    fun getTeam() : LiveData<List<Team>>{
        return listTeam
    }
}