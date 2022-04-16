package com.wish.movieapp.ui.favorite.tvshow

import androidx.lifecycle.ViewModel
import com.wish.movieapp.data.MovieCatalogueRepository
import com.wish.movieapp.data.local.entity.MovieEntity
import com.wish.movieapp.data.local.entity.TvShowEntity

class FavoriteTvShowViewModel(private val repository: MovieCatalogueRepository) : ViewModel() {
    fun getFavTvShows() = repository.getFavoriteTvShows()

    fun setFavTvShow(tvShowEntity: TvShowEntity) {
        val newState = !tvShowEntity.isFav
        repository.setFavoriteTvShow(tvShowEntity, newState)
    }
}