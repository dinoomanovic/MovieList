package com.odin.movielist.utils

import okhttp3.OkHttpClient
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit

/**
 * Created by Dino Omanovic on Mar 16, 2017
 */

class MoviesRestAdapter {
    fun createService(): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    companion object {
        private val BASE_URL = "http://www.omdbapi.com/"
        private val builder = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        private val retrofit = builder.build()
        private val httpClient = OkHttpClient.Builder()
    }
}

