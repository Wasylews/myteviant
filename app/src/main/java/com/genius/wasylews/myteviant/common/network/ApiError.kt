package com.genius.wasylews.myteviant.common.network

const val UNAUTHORIZED = 401

sealed class ApiError(
    val message: String,
    val cause: Throwable?
) {

    class Unauthorized(
        message: String,
        cause: Throwable?
    ) : ApiError(message, cause)

    class NoInternet(
        message: String,
        cause: Throwable?
    ) : ApiError(message, cause)

    class Unknown(
        message: String,
        cause: Throwable? = null
    ) : ApiError(message, cause)
}

