package com.aas.footballapp.test

import com.aas.footballapp.TestContextProvider
import com.aas.footballapp.data.model.Event
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.ui.search.SearchInterface
import com.aas.footballapp.ui.search.SearchMatchResponseModel
import com.aas.footballapp.ui.search.SearchPresenter
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

class SearchMatchTest {
    @Mock
    private lateinit var mInterface: SearchInterface

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var mPresenter: SearchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mPresenter = SearchPresenter(mInterface, apiRepository, gson,
            TestContextProvider()
        )
    }

    @Test
    fun testSearchMatch(){
        val events: MutableList<Event> = mutableListOf()
        val response = SearchMatchResponseModel(events)
        val key = "man united"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("", SearchMatchResponseModel::class.java)
            ).thenReturn(response)

            mPresenter.searchEvent(key)
            verify(mInterface).showLoading()
            verify(mInterface).searchMatchData(events)
            verify(mInterface).hideLoading()
        }
    }
}