package com.aas.footballapp.test

import com.aas.footballapp.TestContextProvider
import com.aas.footballapp.data.model.Event
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.ui.match.nextleaguematch.NextMatchInterface
import com.aas.footballapp.ui.match.nextleaguematch.NextMatchPresenter
import com.aas.footballapp.ui.match.nextleaguematch.NextMatchResponseModel
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

class NextMatchTest {
    @Mock
    private lateinit var mInterface: NextMatchInterface

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var mPresenter: NextMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mPresenter = NextMatchPresenter(mInterface, apiRepository,gson, TestContextProvider())
    }

    @Test
    fun testNextMatch(){
        val event: MutableList<Event> = mutableListOf()
        val response = NextMatchResponseModel(event)
        val idLeague = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", NextMatchResponseModel::class.java))
                .thenReturn(response)
        }

        mPresenter.loadNextMatch(idLeague)
        verify(mInterface).showLoading()
        verify(mInterface).nextMatchData(event)
        verify(mInterface).hideLoading()
    }

}