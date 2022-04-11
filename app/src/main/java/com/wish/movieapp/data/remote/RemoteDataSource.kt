package com.wish.movieapp.data.remote

import com.wish.movieapp.data.remote.response.movie.Movie
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wish.movieapp.data.remote.response.movie.MoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.wish.movieapp.BuildConfig.API_KEY
import com.wish.movieapp.data.remote.response.tv.TvShow
import com.wish.movieapp.data.remote.response.tv.TvShowResponse
import com.wish.movieapp.network.ApiConfig
import com.wish.movieapp.utils.EspressoIdlingResource

class RemoteDataSource {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
                instance ?: synchronized(this) {
                    instance ?: RemoteDataSource()
                }
    }

    fun getMovies(): LiveData<ApiResponse<List<Movie>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<Movie>>>()
        val client = ApiConfig.getApiService().getMovies(API_KEY)

        client.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                resultMovies.value = ApiResponse.success(response.body()?.results as List<Movie>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getMovies onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultMovies
    }


    fun getTvShows(): LiveData<ApiResponse<List<TvShow>>> {
        EspressoIdlingResource.increment()
        val resultTvShows = MutableLiveData<ApiResponse<List<TvShow>>>()
        val client = ApiConfig.getApiService().getTvShows(API_KEY)

        client.enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                resultTvShows.value = ApiResponse.success(response.body()?.results as List<TvShow>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getTvShows onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultTvShows
    }
}