package com.odin.movielist.Utils;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by mobil on 16.03.2017.
 */

public class MoviesRestAdapter {
    private static final String BASE_URL = "http://www.omdbapi.com/";
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = builder.build();
    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();
    public MoviesApi createService(){
        return retrofit.create(MoviesApi.class);
    }
}

