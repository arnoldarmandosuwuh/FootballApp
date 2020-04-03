package com.gdk.arnold.footballapp.view.listmatch

import com.gdk.arnold.footballapp.data.model.matchlist.EventsItem


interface ListMatchView {

    fun hideLoading()
    fun showLoading()
    fun displayFootballMatch(matchList: List<EventsItem>)


}