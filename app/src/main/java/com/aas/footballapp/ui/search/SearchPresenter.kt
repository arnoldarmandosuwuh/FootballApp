package com.aas.footballapp.ui.search

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

class SearchPresenter (
    private val mInterface: SearchInterface,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
){
    fun searchEvent(query: String){
        mInterface.showLoading()

        GlobalScope.launch(contextPool.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(SportDbApi.searchEvent(query)).await(),
                SearchMatchResponseModel::class.java
            )
            data.event?.let { mInterface.searchMatchData(it) }
            mInterface.hideLoading()
        }
    }

    fun searchTeam(query: String){
        mInterface.showLoading()

        GlobalScope.launch(contextPool.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(SportDbApi.searchTeam(query)).await(),
                SearchTeamResponseModel::class.java
            )
            data.teams?.let { mInterface.searchTeamData(it) }
            mInterface.hideLoading()
        }
    }
}