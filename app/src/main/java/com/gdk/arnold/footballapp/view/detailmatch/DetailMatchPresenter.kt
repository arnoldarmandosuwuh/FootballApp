package com.gdk.arnold.footballapp.view.detailmatch

import com.gdk.arnold.footballapp.data.ApiRepository
import com.gdk.arnold.footballapp.data.TheSportDBApi
import com.gdk.arnold.footballapp.data.model.matchdetail.ClubResponse
import com.gdk.arnold.footballapp.view.favoritematch.FavMatchDetailActivity
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailMatchPresenter(
    private val view: DetailActivity,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getDetailMatch(homeTeam: String, awayTeam: String) {
        view.showLoading()
        doAsync {
            val dataHome = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getDetailMatch(homeTeam)),
                ClubResponse::class.java
            )
            val dataAway = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getDetailMatch(awayTeam)),
                ClubResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showMatchBadge(dataHome.teams[0].strTeamBadge!!, dataAway.teams[0].strTeamBadge!!)
            }
        }
    }
}

