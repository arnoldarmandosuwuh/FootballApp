package com.aas.footballapp.ui.favorite.favoriteevent

import android.content.Context
import com.aas.footballapp.data.local.database
import com.aas.footballapp.data.local.event.EventDb
import com.aas.footballapp.data.model.Event
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteEventPresenter(private val mInterface: FavoriteEventInterface) {
    private var listEvent = mutableListOf<Event>()

    fun getFavoriteEvent(context: Context) {
        mInterface.showLoading()

        context.database.use {
            listEvent.clear()

            val result = select(
                EventDb.TABLE_EVENT
            )
            val favorite = result.parseList(classParser<EventDb>())
            for (i in 0 until favorite.size) {
                listEvent.add(
                    Event(
                        favorite[i].idEvent,
                        favorite[i].strEvent,
                        favorite[i].strHomeTeam,
                        favorite[i].strAwayTeam,
                        favorite[i].intHomeScore,
                        favorite[i].intAwayScore,
                        favorite[i].dateEvent,
                        favorite[i].idHomeTeam,
                        favorite[i].idAwayTeam
                    )
                )
            }
        }
        mInterface.hideLoading()
        mInterface.favoriteEvent(listEvent)
    }
}