package com.aas.footballapp.ui.detail.league

import com.google.gson.annotations.SerializedName

data class DetailLeague(
    val idLeague: String,
    val strLeague: String,
    val strDescriptionEN: String,
    val strBadge: String)

data class DetailLeagueResponse(
    val leagues: List<DetailLeague>?
)