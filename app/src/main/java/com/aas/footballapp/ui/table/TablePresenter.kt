package com.aas.footballapp.ui.table

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

class TablePresenter (
    private val tableInterface: TableInterface,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
){
    fun loadTable(id: String?, season: String){
        tableInterface.showLoading()

        GlobalScope.launch(contextPool.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(SportDbApi.loadTable(id, season)).await(),
                TableResponseModel::class.java
            )
            data.table?.let { tableInterface.tableData(it) }
            tableInterface.hideLoading()
        }
    }
}