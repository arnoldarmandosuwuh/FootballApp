package com.aas.footballapp.ui.detail.match

import com.aas.footballapp.data.model.Team

interface DetailMatchInterface{
    fun showLoading()
    fun hideLoading()
    fun showEvent(event: List<DetailMatch>)
    fun showHomeBadge (team: List<Team>)
    fun showAwayBadge (team: List<Team>)
}