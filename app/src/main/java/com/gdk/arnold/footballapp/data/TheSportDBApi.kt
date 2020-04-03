package com.gdk.arnold.footballapp.data

import android.net.Uri
import com.gdk.arnold.footballapp.BuildConfig

object TheSportDBApi {

    private val  SUB_FOLDER = "api/v1/json/"

    fun getEventNextMatch(id: String?): String {
        return BuildConfig.BASE_URL + SUB_FOLDER + BuildConfig.TSDB_API_KEY + "/eventsnextleague.php?id=$id"
    }

    fun getEventLastMatch(id: String?): String {
        return BuildConfig.BASE_URL + SUB_FOLDER + BuildConfig.TSDB_API_KEY + "/eventspastleague.php?id=$id"
    }

    fun getDetailMatch(id: String?): String {
        return BuildConfig.BASE_URL + SUB_FOLDER + BuildConfig.TSDB_API_KEY + "/lookupteam.php?id=$id"
    }
}
