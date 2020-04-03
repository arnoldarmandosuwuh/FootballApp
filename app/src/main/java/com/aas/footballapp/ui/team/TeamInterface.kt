package com.aas.footballapp.ui.team

import com.aas.footballapp.data.model.Team

interface TeamInterface{
    fun showLoading()
    fun hideLoading()
    fun teamData(team: List<Team>)
}