package com.aas.footballapp.ui.favorite.favoriteteam

import com.aas.footballapp.data.model.Team

interface FavoriteTeamInterface {
    fun showLoading()
    fun hideLoading()
    fun favoriteTeam(team: List<Team>)
}