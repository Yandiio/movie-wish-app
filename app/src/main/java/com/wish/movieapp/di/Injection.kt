package com.wish.movieapp.di

import android.content.Context
import com.wish.movieapp.data.MovieCatalogueRepository
import com.wish.movieapp.data.local.LocalDataSource
import com.wish.movieapp.data.local.room.FilmDatabase
import com.wish.movieapp.data.remote.RemoteDataSource
import com.wish.movieapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieCatalogueRepository {
        val database = FilmDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.filmDao())
        val appExecutors = AppExecutors()
        return MovieCatalogueRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}