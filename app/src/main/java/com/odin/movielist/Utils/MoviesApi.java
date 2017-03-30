package com.odin.movielist.Utils;
import com.odin.movielist.Models.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
/**
 * Created by mobil on 16.03.2017.
 */

public interface MoviesApi {
    @GET("/")
    Call<Movies> getMovies(
            @Query("s") String movieName,
            @Query("type") String movieType

    );
}
