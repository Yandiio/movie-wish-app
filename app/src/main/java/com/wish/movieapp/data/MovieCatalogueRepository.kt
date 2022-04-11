package com.wish.movieapp.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.wish.movieapp.data.local.entity.MovieEntity
import com.wish.movieapp.data.local.entity.TvShowEntity
import com.wish.movieapp.data.remote.ApiResponse
import com.wish.movieapp.data.remote.RemoteDataSource
import com.wish.movieapp.data.remote.response.movie.Movie
import com.wish.movieapp.data.remote.response.tv.TvShow
import com.wish.movieapp.utils.AppExecutors
import com.wish.movieapp.vo.Resource

class MovieCatalogueRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : MovieCatalogueDataSource {

    companion object {
        @Volatile
        private var instance: MovieCatalogueRepository? = null
        fun getInstance(remoteData: RemoteDataSource, appExecutors: AppExecutors): MovieCatalogueRepository =
            instance ?: synchronized(this) {
                instance ?: MovieCatalogueRepository(remoteData, appExecutors)
            }
        }

    override fun getMovies(sort: String): LiveData<Resource<PagingData<MovieEntity>>> {
        return object : NetworkBoundResource<PagingData<MovieEntity>, List<Movie>>(appExecutors) {
            override fun shouldFetch(data: PagingData<MovieEntity>?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<List<Movie>>> =
                remoteDataSource.getMovies()

            override fun loadFromDb(): LiveData<PagingData<MovieEntity>> {
                TODO("Not yet implemented")
            }

            override fun saveCallResult(data: List<Movie>) {
                TODO("Not yet implemented")
            }
        }.asLiveData()
    }



    override fun getTvShows(sort: String): LiveData<Resource<PagingData<TvShowEntity>>> {
        return object : NetworkBoundResource<PagingData<TvShowEntity>, List<TvShow>>(appExecutors) {
            override fun shouldFetch(data: PagingData<TvShowEntity>?): Boolean =
                data == null

            override fun loadFromDb(): LiveData<PagingData<TvShowEntity>> {
                TODO("Not yet implemented")
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShow>>> = remoteDataSource.getTvShows()

            override fun saveCallResult(data: List<TvShow>) {
                TODO("Not yet implemented")
            }
        }.asLiveData()
    }


    override fun getDetailMovie(movieId: Int): LiveData<Resource<MovieEntity>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteMovies(): LiveData<PagingData<MovieEntity>> {
        TODO("Not yet implemented")
    }

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getDetailTvShow(tvShowId: Int): LiveData<Resource<TvShowEntity>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteTvShows(): LiveData<PagingData<TvShowEntity>> {
        TODO("Not yet implemented")
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean) {
        TODO("Not yet implemented")
    }
}