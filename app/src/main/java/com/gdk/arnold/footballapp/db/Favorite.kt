package com.gdk.arnold.footballapp.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Favorite(
    val id: Long?,
    val idEvent: String?,
    val strEvent: String?,
    val dateEvent: String?,
    val idHomeTeam: String?,
    val strHomeTeam: String?,
    val intHomeScore: String?,
    val strHomeGoalDetails: String?,
    val idAwayTeam: String?,
    val strAwayTeam: String?,
    val intAwayScore: String?,
    val strAwayGoalDetails: String?,
    //Home Player
    val strHomeLineupGoalkeeper: String?,
    val strHomeLineupDefense: String?,
    val strHomeLineupMidfield: String?,
    val strHomeLineupForward: String?,
    val strHomeLineupSubstitutes: String?,
    //Away Player
    val strAwayLineupGoalkeeper: String?,
    val strAwayLineupDefense: String?,
    val strAwayLineupMidfield: String?,
    val strAwayLineupForward: String?,
    val strAwayLineupSubstitutes: String?,
    val matchType: String = "Upcoming Match"
) : Parcelable {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_NAME: String = "EVENT_NAME"
        const val DATE_EVENT: String = "DATE_EVENT"
        const val HOME_ID: String = "HOME_ID"
        const val HOME_NAME: String = "HOME_NAME"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val HOME_SCORER: String = "HOME_SCORER"
        const val AWAY_ID: String = "AWAY_ID"
        const val AWAY_NAME: String = "AWAY_NAME"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val AWAY_SCORER: String = "AWAY_SCORER"
        const val HOME_GK: String = "HOME_GK"
        const val HOME_DEF: String = "HOME_DEF"
        const val HOME_MID: String = "HOME_MID"
        const val HOME_FOR: String = "HOME_FOR"
        const val HOME_SUB: String = "HOME_SUB"
        const val AWAY_GK: String = "AWAY_GK"
        const val AWAY_DEF: String = "AWAY_DEF"
        const val AWAY_MID: String = "AWAY_MID"
        const val AWAY_FOR: String = "AWAY_FOR"
        const val AWAY_SUB: String = "AWAY_SUB"
        const val MATCH_TYPE: String = "MATCH_TYPE"
    }

}