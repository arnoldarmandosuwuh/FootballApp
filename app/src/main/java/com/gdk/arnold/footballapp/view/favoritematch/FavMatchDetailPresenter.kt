package com.gdk.arnold.footballapp.view.favoritematch

import com.gdk.arnold.footballapp.data.ApiRepository
import com.gdk.arnold.footballapp.data.TheSportDBApi
import com.gdk.arnold.footballapp.data.model.matchdetail.ClubResponse
import com.gdk.arnold.footballapp.util.CoroutineContextProvider
import com.gdk.arnold.footballapp.view.detailmatch.DetailActivity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FavMatchDetailPresenter (
    private val view: FavMatchDetailActivity,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getDetailHome(homeTeam: String) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main){
            val dataHome = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getDetailMatch(homeTeam)).await(),
                ClubResponse::class.java
            )

                view.hideLoading()
                view.showBadgeHome(dataHome.teams[0].strTeamBadge!!)

        }
    }
    fun getDetailAway(awayTeam: String) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main){
            val dataAway = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getDetailMatch(awayTeam)).await(),
                ClubResponse::class.java
            )

                view.hideLoading()
                view.showBadgeAway(dataAway.teams[0].strTeamBadge!!)

        }
    }
}