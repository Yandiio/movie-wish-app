package com.wish.movieapp.ui.favorite.movie

import androidx.lifecycle.ViewModel
import com.wish.movieapp.data.MovieCatalogueRepository
import com.wish.movieapp.data.local.entity.MovieEntity

class FavoriteMovieViewModel(private val repository: MovieCatalogueRepository) : ViewModel() {
    fun getFavMovies() = repository.getFavoriteMovies()

    fun setFavMovie(movieEntity: MovieEntity) {
        val newState = !movieEntity.isFav
        repository.setFavoriteMovie(movieEntity, newState)
    }}