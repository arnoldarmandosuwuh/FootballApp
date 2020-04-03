package com.aas.footballapp.test

import com.aas.footballapp.TestContextProvider
import com.aas.footballapp.data.model.Team
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.ui.detail.team.DetailTeamInterface
import com.aas.footballapp.ui.detail.team.DetailTeamPresenter
import com.aas.footballapp.ui.detail.team.DetailTeamResponseModel
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

class DetailTeamTest {
    @Mock
    private lateinit var mInterface: DetailTeamInterface

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var mPresenter: DetailTeamPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mPresenter = DetailTeamPresenter(mInterface, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testDetailTeam(){
        val teams: MutableList<Team> = mutableListOf()
        val response = DetailTeamResponseModel(teams)
        val id = "133612"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("",DetailTeamResponseModel::class.java))
                .thenReturn(response)
        }

        mPresenter.loadTeamDetail(id)
        verify(mInterface).showLoading()
        verify(mInterface).loadTeam(teams)
        verify(mInterface).hideLoading()
    }
}