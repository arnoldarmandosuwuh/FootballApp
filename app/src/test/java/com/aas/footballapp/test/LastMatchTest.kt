package com.aas.footballapp.test

import com.aas.footballapp.TestContextProvider
import com.aas.footballapp.data.model.Event
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.ui.match.lastleaguematch.LastMatchInterface
import com.aas.footballapp.ui.match.lastleaguematch.LastMatchPresenter
import com.aas.footballapp.ui.match.lastleaguematch.LastMatchResponseModel
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

class LastMatchTest {
    @Mock
    private lateinit var mInterface: LastMatchInterface

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var mPresenter: LastMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mPresenter = LastMatchPresenter(mInterface, apiRepository,gson, TestContextProvider())
    }

    @Test
    fun testNextMatch(){
        val event: MutableList<Event> = mutableListOf()
        val response = LastMatchResponseModel(event)
        val idLeague = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", LastMatchResponseModel::class.java))
                .thenReturn(response)
        }

        mPresenter.loadLastMatch(idLeague)
        verify(mInterface).showLoading()
        verify(mInterface).lastMatchData(event)
        verify(mInterface).hideLoading()
    }
}