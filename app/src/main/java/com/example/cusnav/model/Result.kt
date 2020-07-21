package com.example.retrofitmovies.model

data class Result(
    var adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {


    fun getFavStatus(): String {

        return adult.toString()
    }

    fun setFavStatus(favStatus: String?) {
        if (favStatus != null) {
            this.adult=favStatus.toBoolean()
        }
    }
}