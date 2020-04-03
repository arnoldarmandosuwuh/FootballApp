package com.gdk.arnold.footballapp.view.detailmatch

import com.gdk.arnold.footballapp.data.ApiRepository
import com.gdk.arnold.footballapp.data.TheSportDBApi
import com.gdk.arnold.footballapp.data.model.matchdetail.ClubResponse
import com.gdk.arnold.footballapp.util.CoroutineContextProvider
import com.gdk.arnold.footballapp.view.favoritematch.FavMatchDetailActivity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailMatchPresenter(
    private val view: DetailMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getDetailHome(homeTeam: String) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val dataHome = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getDetailMatch(homeTeam)).await(),
                ClubResponse::class.java
            )

            view.showBadgeHome(dataHome.teams[0].strTeamBadge as String)
            view.hideLoading()
        }
    }
    fun getDetailAway(awayTeam: String) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val dataAway = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getDetailMatch(awayTeam)).await(),
                ClubResponse::class.java
            )

            view.showBadgeAway(dataAway.teams[0].strTeamBadge as String)
            view.hideLoading()
        }
    }
}

