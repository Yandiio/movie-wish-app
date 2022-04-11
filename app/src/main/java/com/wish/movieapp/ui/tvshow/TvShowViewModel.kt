package com.wish.movieapp.ui.tvshow

import androidx.lifecycle.ViewModel
import com.wish.movieapp.data.MovieCatalogueRepository

class TvShowViewModel(private val tvShowViewModel: MovieCatalogueRepository) : ViewModel() {
    fun getTvShow(sort: String) = tvShowViewModel.getTvShows(sort)
}