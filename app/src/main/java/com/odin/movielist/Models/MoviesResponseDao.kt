package com.odin.movielist.models

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Created by Dino Omanovic on May 26, 2019
 */
@Dao
interface MoviesResponseDao {
    @Query("SELECT * FROM movies_response")
    fun getMoviesResponse() : List<Search>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(moviesResponse: Search)

    @Delete
    fun deleteMovie(moviesResponse: Search)
}