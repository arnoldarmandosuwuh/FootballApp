package com.aas.footballapp.ui.detail.team

import com.aas.footballapp.data.model.Team

interface DetailTeamInterface {
    fun showLoading()
    fun hideLoading()
    fun loadTeam(team: List<Team>)
}