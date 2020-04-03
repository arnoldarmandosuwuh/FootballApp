package com.aas.footballapp.data.local.team

data class TeamDb(
    var id: Long? = -1,
    val idTeam: String,
    val strTeam: String,
    val strDescriptionEN: String,
    val strTeamBadge: String
){
    companion object{
        const val TABLE_TEAM: String = "TABLE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_DESCRIPTION: String = "TEAM_DESCRIPTION"
        const val TEAM_BADGGE: String = "TEAM_BADGGE"
    }
}