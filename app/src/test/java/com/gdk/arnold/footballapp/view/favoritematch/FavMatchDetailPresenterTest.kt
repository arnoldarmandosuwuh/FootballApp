package com.gdk.arnold.footballapp.view.favoritematch

import com.gdk.arnold.footballapp.TestContextProvider
import com.gdk.arnold.footballapp.data.ApiRepository
import com.gdk.arnold.footballapp.data.TheSportDBApi
import com.gdk.arnold.footballapp.data.model.matchdetail.ClubResponse
import com.gdk.arnold.footballapp.data.model.matchdetail.TeamsItem
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FavMatchDetailPresenterTest {

    @Mock
    private
    lateinit var view: FavMatchDetailActivity

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: FavMatchDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = FavMatchDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getDetailHome() {
        val events: MutableList<TeamsItem> = mutableListOf()
        val response = ClubResponse(events)
        val id = "133604"

        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getDetailMatch(id)).await(),
                    ClubResponse::class.java
                )
            ).thenReturn(response)

            presenter.getDetailHome(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showBadgeAway(events as String)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getDetailAway() {
        val events: MutableList<TeamsItem> = mutableListOf()
        val response = ClubResponse(events)
        val id = "1234"

        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getDetailMatch(id)).await(),
                    ClubResponse::class.java
                )
            ).thenReturn(response)

            presenter.getDetailAway(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showBadgeAway(events as String)
            Mockito.verify(view).hideLoading()
        }
    }
}