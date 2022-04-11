package com.wish.movieapp.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.wish.movieapp.data.local.entity.MovieEntity
import com.wish.movieapp.data.local.entity.TvShowEntity
import com.wish.movieapp.vo.Resource

interface MovieCatalogueDataSource {
    fun getMovies(sort: String): LiveData<Resource<PagingData<MovieEntity>>>

    fun getDetailMovie(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getFavoriteMovies(): LiveData<PagingData<MovieEntity>>

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)

    fun getTvShows(sort: String): LiveData<Resource<PagingData<TvShowEntity>>>

    fun getDetailTvShow(tvShowId: Int): LiveData<Resource<TvShowEntity>>

    fun getFavoriteTvShows(): LiveData<PagingData<TvShowEntity>>

    fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean)
}