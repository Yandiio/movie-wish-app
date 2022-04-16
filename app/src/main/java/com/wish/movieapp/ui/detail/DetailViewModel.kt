package com.wish.movieapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wish.movieapp.data.MovieCatalogueRepository
import com.wish.movieapp.data.local.entity.MovieEntity
import com.wish.movieapp.data.local.entity.TvShowEntity
import com.wish.movieapp.vo.Resource

class DetailViewModel(private val movieCatalogueRepository: MovieCatalogueRepository) : ViewModel() {
    companion object {
        const val MOVIE = "movie"
        const val TV_SHOW = "tv_show"
    }

    private lateinit var detailTvShow: LiveData<Resource<TvShowEntity>>
    private lateinit var detailMovie: LiveData<Resource<MovieEntity>>

    fun setDetail(id: String, category: String) {
        when (category) {
            TV_SHOW -> {
                detailTvShow = movieCatalogueRepository.getDetailTvShow(id.toInt())
            }
            MOVIE -> {
                detailMovie = movieCatalogueRepository.getDetailMovie(id.toInt())
            }
        }
    }

    fun setFavoriteMovie() {
        val resource = detailMovie.value
        if (resource?.data != null) {
            val newState = !resource.data.isFav
            movieCatalogueRepository.setFavoriteMovie(resource.data, newState)
        }
    }

    fun setFavoriteTvShow() {
        val resource = detailTvShow.value
        if (resource?.data != null) {
            val newState = !resource.data.isFav
            movieCatalogueRepository.setFavoriteTvShow(resource.data, newState)
        }
    }

    fun getDetailMovie() = detailMovie

    fun getDetailTvShow() = detailTvShow
}