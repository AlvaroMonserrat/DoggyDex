package com.rrat.doggydex.core.api


sealed class ApiResponseStatus<T> {
    data class Success<T>(val data: T): ApiResponseStatus<T>()
    class Loading<T>: ApiResponseStatus<T>()
    data class Error<T>(val message: String): ApiResponseStatus<T>()
}