package com.aas.footballapp.ui.detail.league

interface DetailLeagueInterface {
    fun showLoading()
    fun hideLoading()
    fun showLeague(league: List<DetailLeague>)
}