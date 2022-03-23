package com.genius.wasylews.myteviant.common.network

import android.content.Context
import com.genius.wasylews.myteviant.R
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.mapError
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

typealias ApiResult<T> = Result<T, ApiError>

internal class ApiResultResolver @Inject constructor(
    private val connectivity: Connectivity,
    @ApplicationContext private val context: Context
) {
    internal fun <T> execute(call: Call<T>): ApiResult<T> {
        val response = tryCatch { call.execute() }.getOrElse {
            return Err(toApiError(it, response = null))
        }
        return toApiResult(response)
    }

    fun <T> toApiResult(response: Response<T>): ApiResult<T> =
        if (response.isSuccessful) {
            tryCatch { response.body()!! }
                .mapError { toApiError(it, response) }
        } else {
            Err(toApiError(null, response))
        }

    fun toApiError(throwable: Throwable?, response: Response<*>?): ApiError =
        when {
            // just in case
            throwable is ApiError -> throwable

            response?.code() == UNAUTHORIZED -> ApiError.Unauthorized(
                message = context.getUnauthorizedMessage(), cause = throwable
            )

            throwable.isOffline(connectivity) -> ApiError.NoInternet(
                message = context.getInternetUnavailableMessage(), cause = throwable)

            else -> {
                ApiError.Unknown(
                    message = context.getUnknownErrorMessage(), cause = throwable)
            }
        }
}

private fun Throwable?.isOffline(connectivity: Connectivity): Boolean =
    connectivity.isOffline ||
        this is SocketTimeoutException || this is UnknownHostException || this is ConnectException


private fun Context.getInternetUnavailableMessage() =
    this.getString(R.string.internet_unavailable)

private fun Context.getUnknownErrorMessage() =
    this.getString(R.string.unknown_error)

private fun Context.getUnauthorizedMessage() =
    this.getString(R.string.user_unauthorized)