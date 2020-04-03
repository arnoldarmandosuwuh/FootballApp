package com.aas.footballapp.ui.detail.match

data class DetailMatch(
    val idEvent: String? = "",
    val strEvent: String? = "",
    val strHomeTeam: String? = "",
    val strAwayTeam: String? = "",
    val intHomeScore: String? = "",
    val intAwayScore: String? = "",
    val strHomeLineupGoalkeeper: String? = "",
    val strHomeLineupDefense: String? = "",
    val strHomeLineupMidfield: String? = "",
    val strHomeLineupForward: String? = "",
    val strHomeLineupSubstitutes: String? = "",
    val strAwayLineupGoalkeeper: String? = "",
    val strAwayLineupDefense: String? = "",
    val strAwayLineupMidfield: String? = "",
    val strAwayLineupForward: String? = "",
    val strAwayLineupSubstitutes: String? = "",
    val strHomeGoalDetails: String? = "",
    val strAwayGoalDetails: String? = "",
    val dateEvent: String? = "",
    val idHomeTeam: String? = "",
    val idAwayTeam: String? = ""
)


data class DetailMatchResponse(
    val events: List<DetailMatch>?
)