package com.aas.footballapp.ui.search

import com.aas.footballapp.data.model.Event
import com.aas.footballapp.data.model.Team

data class SearchMatchResponseModel(val event: List<Event>?)

data class SearchTeamResponseModel(val teams: List<Team>?)