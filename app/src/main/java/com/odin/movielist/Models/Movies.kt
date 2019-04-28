package com.odin.movielist.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Dino Omanovic on Mar 16, 2017
 */

class Movies
/**
 *
 * @param response
 * @param totalResults
 * @param search
 */(search: List<Search>, totalResults: String, response: String) : Serializable {
    @SerializedName("Search")
    @Expose
    var search: List<Search>? = search
    @SerializedName("totalResults")
    @Expose
    var totalResults: String? = totalResults
    @SerializedName("Response")
    @Expose
    var response: String? = response

    companion object {
        private const val serialVersionUID = -6002195718440599520L
    }
}
