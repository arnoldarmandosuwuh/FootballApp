package com.aas.footballapp.ui.favorite.favoriteteam

import android.content.Context
import com.aas.footballapp.data.local.database
import com.aas.footballapp.data.local.team.TeamDb
import com.aas.footballapp.data.model.Team
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteTeamPresenter(private val mInterface: FavoriteTeamInterface) {
    private var listTeam = mutableListOf<Team>()

    fun getFavoriteTeam(context: Context) {
        mInterface.showLoading()

        context.database.use {
            listTeam.clear()

            val result = select(TeamDb.TABLE_TEAM)
            val favorite = result.parseList(classParser<TeamDb>())
            for (i in 0 until favorite.size) {
                listTeam.add(
                    Team(
                        favorite[i].idTeam,
                        favorite[i].strTeam,
                        favorite[i].strDescriptionEN,
                        favorite[i].strTeamBadge
                    )
                )
            }
        }
        mInterface.hideLoading()
        mInterface.favoriteTeam(listTeam)
    }
}