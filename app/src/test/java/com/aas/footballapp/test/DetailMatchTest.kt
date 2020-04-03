package com.aas.footballapp.test

import com.aas.footballapp.TestContextProvider
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.ui.detail.match.DetailMatch
import com.aas.footballapp.ui.detail.match.DetailMatchInterface
import com.aas.footballapp.ui.detail.match.DetailMatchPresenter
import com.aas.footballapp.ui.detail.match.DetailMatchResponse
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

class DetailMatchTest {
    @Mock
    private lateinit var mInterface: DetailMatchInterface

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var mPresenter: DetailMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mPresenter = DetailMatchPresenter(mInterface, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testDetailMatch(){
        val event: MutableList<DetailMatch> = mutableListOf()
        val response = DetailMatchResponse(event)
        val idMatch = "602438"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", DetailMatchResponse::class.java))
                .thenReturn(response)
        }

        mPresenter.loadMatchDetail(idMatch)
        verify(mInterface).showLoading()
        verify(mInterface).showEvent(event)
        verify(mInterface).hideLoading()
    }

}