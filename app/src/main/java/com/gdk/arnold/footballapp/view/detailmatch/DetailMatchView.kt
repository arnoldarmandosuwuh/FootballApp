package com.gdk.arnold.footballapp.view.detailmatch

interface DetailMatchView {
    fun hideLoading()
    fun showLoading()
    fun showBadgeHome(homeTeam: String)
    fun showBadgeAway(awayTeam: String)

}
