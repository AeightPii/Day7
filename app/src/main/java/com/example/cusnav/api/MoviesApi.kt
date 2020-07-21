package com.example.myfavapi.api

import com.example.retrofitmovies.model.Movies
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesApi{
    //this is for singleton
    private val moviesApiInterface : MovieApiInterface

    companion object{ //class name nae direct call lo ya tl obj build sa yr ma lo
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
    }
    init {
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        moviesApiInterface=retrofit.create(MovieApiInterface::class.java)
    }

    fun getMovies(apiKey:String):Call<Movies>{
        return moviesApiInterface.getMovies(apiKey)
    }

}