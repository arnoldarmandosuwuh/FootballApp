package com.aas.footballapp.ui.match.lastleaguematch

import com.aas.footballapp.data.model.Event

interface LastMatchInterface {
    fun showLoading()
    fun hideLoading()
    fun lastMatchData(event: List<Event>)
}