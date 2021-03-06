package com.aas.footballapp.ui.match.nextleaguematch

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

class NextMatchPresenter(
    private val nextMatchInterface: NextMatchInterface,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun loadNextMatch(id: String?) {
        nextMatchInterface.showLoading()

        GlobalScope.launch(contextPool.main){
            val data = gson.fromJson(
                apiRepository.doRequestAsync(SportDbApi.loadNextMatch(id)).await(),
                NextMatchResponseModel::class.java
            )
            data.events?.let { nextMatchInterface.nextMatchData(it) }
            nextMatchInterface.hideLoading()
        }
    }
}