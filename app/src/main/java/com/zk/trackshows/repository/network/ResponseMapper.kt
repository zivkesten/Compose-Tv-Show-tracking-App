package com.zk.trackshows.repository.network

import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Response

sealed class NetworkResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()
}

@ExperimentalCoroutinesApi
fun <T: Any> responseMapper(response: Response<T>): NetworkResult<T> {
    val body = response.body()
    val errorBody = response.errorBody()
    return when {
        body != null -> {
            NetworkResult.Success(body)
        }
        errorBody != null -> {
            NetworkResult.Error(Exception(errorBody.string()))
        }
        else -> {
            NetworkResult.Error(Exception("Unknown error: ${response.raw().message}"))
        }
    }
}

