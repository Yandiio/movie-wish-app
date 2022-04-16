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

    fun getDetailMovie() = detailMovie

    fun getDetailTvShow() = detailTvShow
}