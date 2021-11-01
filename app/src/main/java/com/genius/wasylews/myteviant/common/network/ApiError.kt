package com.genius.wasylews.myteviant.common.network

import okhttp3.Headers
import okhttp3.Headers.Companion.toHeaders

private val emptyHeaders = emptyMap<String, String>().toHeaders()

sealed class ApiError(
    val message: String,
    val cause: Throwable?
) {

    // Default values only for compatibility with legacy code
    data class Response(
        val httpCode: Int,
        val errorMessage: String = "",
        val headers: Headers = emptyHeaders,
    ) : ApiError(errorMessage, null)

    class NoInternet(
        message: String,
        cause: Throwable
    ) : ApiError(message, cause)

    class Unknown(
        message: String,
        cause: Throwable? = null
    ) : ApiError(message, cause)
}

