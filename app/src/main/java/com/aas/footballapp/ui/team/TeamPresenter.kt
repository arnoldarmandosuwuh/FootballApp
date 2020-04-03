package com.aas.footballapp.ui.team

import android.util.Log
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.data.remote.api.SportDbApi
import com.aas.footballapp.helper.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamPresenter(
    private val teamInterface: TeamInterface,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
){
    fun loadTeam(id: String?){
        teamInterface.showLoading()

        GlobalScope.launch(contextPool.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(SportDbApi.loadTeam(id)).await(),
                TeamResponseModel::class.java
            )
            data.teams?.let { teamInterface.teamData(it) }
            teamInterface.hideLoading()
        }
    }
}