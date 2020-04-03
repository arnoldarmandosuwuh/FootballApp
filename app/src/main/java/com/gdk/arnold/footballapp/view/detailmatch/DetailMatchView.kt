package com.gdk.arnold.footballapp.view.detailmatch

interface DetailMatchView {
    fun hideLoading()
    fun showLoading()
    fun showMatchBadge(homeTeam: String, awayTeam: String)

}
