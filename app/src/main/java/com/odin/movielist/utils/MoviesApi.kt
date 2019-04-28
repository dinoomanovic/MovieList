package com.odin.movielist.utils

import com.odin.movielist.models.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Dino Omanovic on Mar 16, 2017
 */

interface MoviesApi {
    @GET("/")
    fun getMovies(
            @Query("s") movieName: String,
            @Query("type") movieType: String

    ): Call<Movies>
}
