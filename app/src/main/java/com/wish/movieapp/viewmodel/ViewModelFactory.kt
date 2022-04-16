package com.wish.movieapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wish.movieapp.data.MovieCatalogueRepository
import com.wish.movieapp.di.Injection
import com.wish.movieapp.ui.detail.DetailViewModel
import com.wish.movieapp.ui.movie.MovieViewModel
import com.wish.movieapp.ui.tvshow.TvShowViewModel

class ViewModelFactory(private val movieCatalogueRepository: MovieCatalogueRepository) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: ViewModelFactory(Injection.provideRepository(context))
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(movieCatalogueRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(movieCatalogueRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(movieCatalogueRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}