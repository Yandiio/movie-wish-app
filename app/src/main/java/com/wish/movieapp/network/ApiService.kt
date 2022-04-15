package com.wish.movieapp.network;

import com.wish.movieapp.data.remote.response.movie.MovieDetailResponse
import com.wish.movieapp.data.remote.response.movie.MoviesResponse
import com.wish.movieapp.data.remote.response.tv.TvShowDetailResponse
import com.wish.movieapp.data.remote.response.tv.TvShowResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey: String
    ): Call<MoviesResponse>

    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ): Call<MovieDetailResponse>

    @GET("discover/tv")
    fun getTvShows(
        @Query("api_key") apiKey: String
    ): Call<TvShowResponse>

    @GET("tv/{id}")
    fun getTvShowDetail(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ): Call<TvShowDetailResponse>
}
