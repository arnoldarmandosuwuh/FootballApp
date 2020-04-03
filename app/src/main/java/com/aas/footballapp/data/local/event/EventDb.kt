package com.aas.footballapp.data.local.event

data class EventDb(
    var id: Long? = -1,
    val idEvent: String,
    val strEvent: String,
    val strHomeTeam: String,
    val strAwayTeam: String,
    val intHomeScore: String,
    val intAwayScore: String,
    val dateEvent: String,
    val idHomeTeam: String,
    val idAwayTeam: String
) {
    companion object {
        const val TABLE_EVENT: String = "TABLE_EVENT"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_NAME: String = "EVENT_NAME"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val HOME_ID: String = "HOME_ID"
        const val AWAY_ID: String = "AWAY_ID"
    }
}