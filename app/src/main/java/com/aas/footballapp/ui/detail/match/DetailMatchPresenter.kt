package com.aas.footballapp.ui.detail.match

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.aas.footballapp.data.local.database
import com.aas.footballapp.data.local.event.EventDb
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.data.remote.api.SportDbApi
import com.aas.footballapp.helper.CoroutineContextProvider
import com.aas.footballapp.ui.detail.team.DetailTeamResponseModel
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMatchPresenter(
    private val detailMatchInterface: DetailMatchInterface,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun loadMatchDetail(id: String) {
        detailMatchInterface.showLoading()
        GlobalScope.launch(contextPool.main){
            val data = gson.fromJson(
                apiRepository.doRequestAsync(
                    SportDbApi.loadDetailMatch(id)
                ).await(), DetailMatchResponse::class.java
            )
            data.events?.let { detailMatchInterface.showEvent(it) }
            detailMatchInterface.hideLoading()
        }
    }

    fun loadHomeBadge(id: String) {
        detailMatchInterface.showLoading()

        GlobalScope.launch(contextPool.main){
            val data = gson.fromJson(
                apiRepository.doRequestAsync(SportDbApi.loadDetailTeam(id)).await(),
                DetailTeamResponseModel::class.java
            )
            data.teams?.let { detailMatchInterface.showHomeBadge(it) }
            detailMatchInterface.hideLoading()
        }
    }

    fun loadAwayBadge(id: String) {
        detailMatchInterface.hideLoading()

        GlobalScope.launch(contextPool.main){
            val data = gson.fromJson(
                apiRepository.doRequestAsync(SportDbApi.loadDetailTeam(id)).await(),
                DetailTeamResponseModel::class.java
            )
            data.teams?.let { detailMatchInterface.showAwayBadge(it) }
            detailMatchInterface.hideLoading()
        }
    }

    fun addFavoriteEvent(
        context: Context,
        idEvent: String,
        strEvent: String,
        strHomeTeam: String,
        strAwayTeam: String,
        intHomeScore: String,
        intAwayScore: String,
        dateEvent: String,
        idHomeTeam: String,
        idAwayTeam: String
    ) {
        try {
            context.database.use {
                insert(
                    EventDb.TABLE_EVENT,
                    EventDb.EVENT_ID to idEvent,
                    EventDb.EVENT_NAME to strEvent,
                    EventDb.HOME_TEAM to strHomeTeam,
                    EventDb.AWAY_TEAM to strAwayTeam,
                    EventDb.HOME_SCORE to intHomeScore,
                    EventDb.AWAY_SCORE to intAwayScore,
                    EventDb.EVENT_DATE to dateEvent,
                    EventDb.HOME_ID to idHomeTeam,
                    EventDb.AWAY_ID to idAwayTeam
                )
            }
            context.toast("Favorite").show()
        } catch (e: SQLiteConstraintException) {
            context.toast(e.localizedMessage).show()
        }
    }

    fun removeFavorite(context: Context, idEvent: String) {
        try {
            context.database.use {
                delete(
                    EventDb.TABLE_EVENT,
                    "(EVENT_ID = {id})",
                    "id" to idEvent
                )
            }
            context.toast("Unfavorite").show()
        } catch (e: SQLiteConstraintException) {
            context.toast(e.localizedMessage).show()
        }
    }

    fun setFavorite(context: Context, idEvent: String): Boolean {
        var isFav = false

        context.database.use {
            val result = select(EventDb.TABLE_EVENT)
                .whereArgs(
                    "(EVENT_ID = {id})",
                    "id" to idEvent
                )
            val fav = result.parseList(classParser<EventDb>())
            if (fav.isNotEmpty()) {
                isFav = true
            }
        }
        return isFav
    }
}