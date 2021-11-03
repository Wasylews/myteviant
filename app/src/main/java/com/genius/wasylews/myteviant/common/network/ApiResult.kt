package com.genius.wasylews.myteviant.common.network

import android.content.Context
import com.genius.wasylews.myteviant.R
import com.genius.wasylews.myteviant.common.tryCatch
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.mapError
import retrofit2.Call
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

typealias ApiResult<T> = Result<T, ApiError>

class ApiResultResolver(
    private val connectivity: Connectivity,
    private val context: Context
) {
    internal fun <T> execute(call: Call<T>): ApiResult<T> {
        val response = tryCatch { call.execute() }.getOrElse {
            return Err(toApiError(it, error = null, call, response = null))
        }
        return toApiResult(response, call)
    }

    fun <T> toApiResult(response: Response<T>, call: Call<*>): ApiResult<T> =
        if (response.isSuccessful) {
            tryCatch { response.body()!! }
                .mapError { toApiError(it, error = null, call, response) }
        } else {
            Err(toApiError(response, call))
        }

    private fun <T> toApiError(response: Response<T>, call: Call<*>): ApiError {
        val error = tryCatch {
            response.errorBody()!!.string()
        }.getOrElse {
            return toApiError(it, error = null, call, response)
        }
        return tryCatch {
            ApiError.Response(
                httpCode = response.code(),
                headers = response.headers(),
                errorMessage = error,
            )
        }.getOrElse { t ->
            toApiError(t, error, call, response)
        }
    }

    fun toApiError(
        throwable: Throwable,
        error: String?,
        call: Call<*>,
        response: Response<*>?,
    ): ApiError =
        when {
            // just in case
            throwable is ApiError -> throwable

            throwable.isOffline(connectivity) -> ApiError.NoInternet(
                message = context.getInternetUnavailableMessage(), cause = throwable)

            else -> {
                ApiError.Unknown(
                    message = context.getUnknownErrorMessage(), cause = throwable)
            }
        }
}

private fun Throwable.isOffline(connectivity: Connectivity): Boolean =
    connectivity.isOffline ||
        this is SocketTimeoutException || this is UnknownHostException || this is ConnectException


private fun Context.getInternetUnavailableMessage() =
    this.getString(R.string.internet_unavailable)

private fun Context.getUnknownErrorMessage() =
    this.getString(R.string.unknown_error)