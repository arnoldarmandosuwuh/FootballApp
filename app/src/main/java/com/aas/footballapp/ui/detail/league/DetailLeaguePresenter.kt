package com.aas.footballapp.ui.detail.league

import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.data.remote.api.SportDbApi
import com.aas.footballapp.helper.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailLeaguePresenter (
    private val detailLeagueInterface: DetailLeagueInterface,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
){
    fun loadLeagueDetail(id: String){
        detailLeagueInterface.showLoading()

        GlobalScope.launch(contextPool.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(SportDbApi.loadLeague(id)).await(),
                DetailLeagueResponse::class.java
            )
            data.leagues?.let { detailLeagueInterface.showLeague(it) }
            detailLeagueInterface.hideLoading()
        }
    }
}