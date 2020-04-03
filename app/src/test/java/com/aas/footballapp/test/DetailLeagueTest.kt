package com.aas.footballapp.test

import com.aas.footballapp.TestContextProvider
import com.aas.footballapp.data.model.League
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.ui.detail.league.DetailLeague
import com.aas.footballapp.ui.detail.league.DetailLeagueInterface
import com.aas.footballapp.ui.detail.league.DetailLeaguePresenter
import com.aas.footballapp.ui.detail.league.DetailLeagueResponse
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DetailLeagueTest {
    @Mock
    private lateinit var mInterface: DetailLeagueInterface

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var mPresenter: DetailLeaguePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mPresenter = DetailLeaguePresenter(mInterface, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testDetailLeague() {
        val league: MutableList<DetailLeague> = mutableListOf()
        val response = DetailLeagueResponse(league)
        val id = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", DetailLeagueResponse::class.java))
                .thenReturn(response)
        }

        mPresenter.loadLeagueDetail(id)
        verify(mInterface).showLoading()
        verify(mInterface).showLeague(league)
        verify(mInterface).hideLoading()
    }
}