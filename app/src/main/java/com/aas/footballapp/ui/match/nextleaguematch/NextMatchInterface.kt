package com.aas.footballapp.ui.match.nextleaguematch

import com.aas.footballapp.data.model.Event

interface NextMatchInterface {
    fun showLoading()
    fun hideLoading()
    fun nextMatchData(event: List<Event>)
}