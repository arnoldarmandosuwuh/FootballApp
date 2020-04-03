package com.aas.footballapp.ui.match.lastteammatch

import com.aas.footballapp.data.model.Event

interface LastTeamMatchInterface {
    fun showLoading()
    fun hideLoading()
    fun lastMatchData(event: List<Event>)
}