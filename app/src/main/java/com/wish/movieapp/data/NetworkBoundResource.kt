package com.wish.movieapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

import com.wish.movieapp.data.remote.ApiResponse;
import com.wish.movieapp.data.remote.StatusResponse;
import com.wish.movieapp.utils.AppExecutors;
import com.wish.movieapp.vo.Resource

import kotlin.Suppress;

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors:AppExecutors) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDb()

        result.addSource(dbSource) { data ->
                result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                        result.value = Resource.success(newData)
                }
            }
        }
    }

    private fun onFetchFailed() {}

    protected abstract fun loadFromDb(): LiveData<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
                result.value = Resource.loading(newData)
        }
        result.addSource(apiResponse) { response ->
                result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                StatusResponse.SUCCESS ->
                mExecutors.diskIO().execute {
                    saveCallResult(response.body)
                    mExecutors.mainThread().execute {
                        result.addSource(loadFromDb()) { newData ->
                                result.value = Resource.success(newData)
                        }
                    }
                }
                StatusResponse.EMPTY -> mExecutors.mainThread().execute {
                    result.addSource(loadFromDb()) { newData ->
                            result.value = Resource.success(newData)
                    }
                }
                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                            result.value = Resource.error(response.message, newData)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result

}