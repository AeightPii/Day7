package com.example.myfavapi.api

import com.example.retrofitmovies.model.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieApiInterface {
   // @Headers("api_key: 59e90160f05dc382b043b086e34c75c5")
    @GET("top_rated")
    fun getMovies(
       @Query("api_key") apiKey:String
   ) :Call<Movies>
}