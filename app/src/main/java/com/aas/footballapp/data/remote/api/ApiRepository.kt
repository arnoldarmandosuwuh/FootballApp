package com.aas.footballapp.data.remote.api

import com.aas.footballapp.BuildConfig
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class ApiRepository {

    fun doRequestAsync(url: String): Deferred<String> = GlobalScope.async {
        URL(url).readText()
    }
}