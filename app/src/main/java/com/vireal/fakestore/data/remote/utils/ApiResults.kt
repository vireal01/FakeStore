package com.vireal.fakestore.data.remote.utils

sealed class ApiResult<out T> {
  data class Success<T>(val data: T) : ApiResult<T>()
  data class Error(val exception: Exception) : ApiResult<Nothing>()
}

fun <T> ApiResult<T>.toResult(): Result<T> {
  return when (this) {
    is ApiResult.Success -> Result.success(data)
    is ApiResult.Error -> Result.failure(exception)
  }
}