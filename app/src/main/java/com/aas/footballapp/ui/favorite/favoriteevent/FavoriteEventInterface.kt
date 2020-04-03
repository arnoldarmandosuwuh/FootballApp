package com.aas.footballapp.ui.favorite.favoriteevent

import com.aas.footballapp.data.model.Event

interface FavoriteEventInterface {
    fun showLoading()
    fun hideLoading()
    fun favoriteEvent(event: List<Event>)
}