package com.aas.footballapp.ui.search

import com.aas.footballapp.data.model.Event
import com.aas.footballapp.data.model.Team

interface SearchInterface {
    fun showLoading()
    fun hideLoading()
    fun searchMatchData(event: List<Event>)
    fun searchTeamData(team: List<Team>)
}