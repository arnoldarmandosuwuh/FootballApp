package com.gdk.arnold.footballapp.view.listmatch

import com.gdk.arnold.footballapp.data.ApiRepository
import com.gdk.arnold.footballapp.data.TheSportDBApi
import com.gdk.arnold.footballapp.data.model.matchlist.EventResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ListMatchPresenter(
    private val view: ListMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    fun getNextMatch(id: String) {
        view.showLoading()
        //view.showLog(TheSportDBApi.getEventNextMatch(id))
        doAsync {
            val dataNext = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getEventNextMatch(id)),
                EventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.displayFootballMatch(dataNext.events)
            }
        }
    }

    fun getLastMatch(id: String) {
        view.showLoading()
        //view.showLog(TheSportDBApi.getEventNextMatch(id))
        doAsync {
            val dataLast = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getEventLastMatch(id)),
                EventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.displayFootballMatch(dataLast.events)
            }
        }
    }
}