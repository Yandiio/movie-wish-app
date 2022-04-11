package com.wish.movieapp.ui.movie

import androidx.lifecycle.ViewModel
import com.wish.movieapp.data.MovieCatalogueRepository

class MovieViewModel(private val movieCatalogueRepository: MovieCatalogueRepository) : ViewModel() {
    fun getMovies(sort: String) = movieCatalogueRepository.getMovies(sort)
}