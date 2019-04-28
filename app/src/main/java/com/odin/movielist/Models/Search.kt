package com.odin.movielist.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Dino Omanovic on Mar 16, 2017
 */

class Search
/**
 *
 * @param title
 * @param poster
 * @param year
 * @param imdbID
 * @param type
 */(title: String, year: String, imdbID: String, type: String, poster: String) : Serializable {
    @SerializedName("Title")
    @Expose
    var title: String? = title
    @SerializedName("Year")
    @Expose
    var year: String? = year
    @SerializedName("imdbID")
    @Expose
    var imdbID: String? = imdbID
    @SerializedName("Type")
    @Expose
    var type: String? = type
    @SerializedName("Poster")
    @Expose
    var poster: String? = poster

    companion object {
        private const val serialVersionUID = -5694370592020319577L
    }
}
