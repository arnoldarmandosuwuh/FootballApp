package com.gdk.arnold.footballapp.view.listmatch

import com.gdk.arnold.footballapp.TestContextProvider
import com.gdk.arnold.footballapp.data.ApiRepository
import com.gdk.arnold.footballapp.data.TheSportDBApi
import com.gdk.arnold.footballapp.data.model.matchlist.EventResponse
import com.gdk.arnold.footballapp.data.model.matchlist.EventsItem
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ListMatchPresenterTest {


    @Mock
    private
    lateinit var view: ListMatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: ListMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ListMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun getNextMatch() {
        val event: MutableList<EventsItem> = mutableListOf()
        val response = EventResponse(event)
        val id = "4328"

        GlobalScope.launch {
            `when` (gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEventNextMatch(id)).await(),
                EventResponse::class.java
            )).thenReturn(response)

            presenter.getNextMatch(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).displayFootballMatch(event)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getLastMatch() {
        val event: MutableList<EventsItem> = mutableListOf()
        val response = EventResponse(event)
        val id = "4328"

        GlobalScope.launch {
            `when` (gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEventLastMatch(id)).await(),
                EventResponse::class.java
            )).thenReturn(response)

            presenter.getLastMatch(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).displayFootballMatch(event)
            Mockito.verify(view).hideLoading()
        }
    }
}