package com.aas.footballapp.ui.match.lastleaguematch

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

class LastMatchPresenter(
    private val lastMatchInterface: LastMatchInterface,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun loadLastMatch(id: String?) {
        lastMatchInterface.showLoading()

        GlobalScope.launch(contextPool.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(SportDbApi.loadLastMatch(id)).await(),
                LastMatchResponseModel::class.java
            )
            data.events?.let { lastMatchInterface.lastMatchData(it) }
            lastMatchInterface.hideLoading()
        }
    }
}