package com.gdk.arnold.footballapp.view.listmatch

import com.gdk.arnold.footballapp.data.ApiRepository
import com.gdk.arnold.footballapp.data.TheSportDBApi
import com.gdk.arnold.footballapp.data.model.matchlist.EventResponse
import com.gdk.arnold.footballapp.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ListMatchPresenter(
    private val view: ListMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getNextMatch(id: String) {
        view.showLoading()

        GlobalScope.launch (Dispatchers.Main){
            val dataNext = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEventNextMatch(id)).await(),
                EventResponse::class.java)

            view.displayFootballMatch(dataNext.events)
            view.hideLoading()
        }
    }

    fun getLastMatch(id: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val dataLast = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getEventLastMatch(id)).await(),
                EventResponse::class.java)

            view.displayFootballMatch(dataLast.events)
            view.hideLoading()

        }
    }
}