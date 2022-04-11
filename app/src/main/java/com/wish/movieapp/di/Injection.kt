package com.wish.movieapp.di

import android.content.Context
import com.wish.movieapp.data.MovieCatalogueRepository
import com.wish.movieapp.data.remote.RemoteDataSource
import com.wish.movieapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieCatalogueRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        val appExecutors = AppExecutors()
        return MovieCatalogueRepository.getInstance(remoteDataSource, appExecutors)
    }
}