package com.aas.footballapp.ui.detail.team

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.aas.footballapp.data.local.database
import com.aas.footballapp.data.local.team.TeamDb
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.data.remote.api.SportDbApi
import com.aas.footballapp.helper.CoroutineContextProvider
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

class DetailTeamPresenter(
    private val detailTeamInterface: DetailTeamInterface,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun loadTeamDetail(id: String) {
        detailTeamInterface.showLoading()

        GlobalScope.launch(contextPool.main){
            val data = gson.fromJson(
                apiRepository.doRequestAsync(SportDbApi.loadDetailTeam(id)).await(),
                DetailTeamResponseModel::class.java
            )
            data.teams?.let { detailTeamInterface.loadTeam(it) }
            detailTeamInterface.hideLoading()
        }
    }

    fun addFavoriteTeam(
        context: Context,
        idTeam: String,
        strTeam: String,
        strDescriptionEN: String,
        strTeamBadge: String
    ) {
        try {
            context.database.use {
                insert(
                    TeamDb.TABLE_TEAM,
                    TeamDb.TEAM_ID to idTeam,
                    TeamDb.TEAM_NAME to strTeam,
                    TeamDb.TEAM_DESCRIPTION to strDescriptionEN,
                    TeamDb.TEAM_BADGGE to strTeamBadge
                )
            }
            context.toast("Favorite").show()
        } catch (e: SQLiteConstraintException) {
            context.toast(e.localizedMessage).show()
        }
    }

    fun removeFavoriteTeam(context: Context, idTeam: String){
        try {
            context.database.use {
                delete(
                    TeamDb.TABLE_TEAM,
                    "(TEAM_ID = {id})",
                    "id" to idTeam
                )
            }
            context.toast("Unfavorite").show()
        } catch (e: SQLiteConstraintException){
            context.toast(e.localizedMessage).show()
        }
    }

    fun setFavorite(context: Context, idTeam: String): Boolean {
        var isFav = false

        context.database.use {
            val result = select(TeamDb.TABLE_TEAM)
                .whereArgs(
                    "(TEAM_ID = {id})",
                    "id" to idTeam
                )
            val fav = result.parseList(classParser<TeamDb>())
            if (fav.isNotEmpty()){
                isFav = true
            }
        }
        return isFav
    }
}