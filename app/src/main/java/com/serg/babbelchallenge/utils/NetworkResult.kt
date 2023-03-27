package com.serg.babbelchallenge.utils

sealed class NetworkResult<out T> {
    object Loading : NetworkResult<Nothing>()
    data class Error(val exception: Exception? = null) : NetworkResult<Nothing>()
    data class Success<out T>(val data: T) : NetworkResult<T>()
}
