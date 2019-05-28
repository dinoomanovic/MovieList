package com.odin.movielist.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Dino Omanovic on Mar 16, 2017
 */
@Entity(tableName = "movies_response")
data class Search
/**
 *
 * @param title
 * @param poster
 * @param year
 * @param imdbID
 * @param type
 */(val Title: String, val Year: String, @PrimaryKey val imdbID: String, val Type: String, val Poster: String)