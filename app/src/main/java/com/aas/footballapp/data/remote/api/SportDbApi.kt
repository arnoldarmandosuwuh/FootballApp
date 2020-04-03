package com.aas.footballapp.data.remote.api

import com.aas.footballapp.BuildConfig
import com.aas.footballapp.ui.detail.league.DetailLeagueResponse
import com.aas.footballapp.ui.detail.match.DetailMatchResponse
import com.aas.footballapp.ui.detail.team.DetailTeamResponseModel
import com.aas.footballapp.ui.match.lastleaguematch.LastMatchResponseModel
import com.aas.footballapp.ui.match.lastteammatch.LastTeamMatchResponseModel
import com.aas.footballapp.ui.match.nextleaguematch.NextMatchResponseModel
import com.aas.footballapp.ui.search.SearchMatchResponseModel
import com.aas.footballapp.ui.search.SearchTeamResponseModel
import com.aas.footballapp.ui.table.TableResponseModel
import com.aas.footballapp.ui.team.TeamResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

object SportDbApi {
    
    private const val BASE_URL: String = BuildConfig.BASE_URL

    fun loadLeague(id: String?): String {
        return "$BASE_URL/lookupleague.php?id=$id"
    }

    fun loadTeam(id: String?): String {
        return "$BASE_URL/lookup_all_teams.php?id=$id"
    }

    fun loadNextMatch(id: String?): String{
        return "$BASE_URL/eventsnextleague.php?id=$id"
    }

    fun loadLastMatch(id: String?): String{
        return "$BASE_URL/eventspastleague.php?id=$id"

    }

    fun loadDetailMatch(id: String?): String{
        return "$BASE_URL/lookupevent.php?id=$id"

    }

    fun loadDetailTeam(id: String?): String{
        return "$BASE_URL/lookupteam.php?id=$id"
    }

    fun loadNext(id: String?): String{
        return "$BASE_URL/eventsnext.php?id=$id"
    }

    fun loadLast(id: String?): String{
        return "$BASE_URL/eventslast.php?id=$id"
    }

    fun loadTable(id: String?, s: String?): String{
        return "$BASE_URL/lookuptable.php?l=$id&s=$s"
    }

    fun searchTeam(t: String?): String{
        return "$BASE_URL/searchteams.php?t=$t"
    }

    fun searchEvent(e: String): String{
        return "$BASE_URL/searchevents.php?e=$e"
    }
}