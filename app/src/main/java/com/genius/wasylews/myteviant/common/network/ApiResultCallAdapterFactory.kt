package com.genius.wasylews.myteviant.common

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResultCallAdapterFactory(
    private val apiResultResolver: ApiResultResolver
): CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {

        getCallApiResultType(returnType)?.let { responseType ->
            return object : CallAdapter<Any, Call<ApiResult<Any>>> {
                override fun responseType(): Type = responseType

                override fun adapt(call: Call<Any>) = ApiResultCall(call, apiResultResolver)
            }
        }
        return null
    }

    private fun getCallApiResultType(returnType: Type): Type? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }
        require(returnType is ParameterizedType) {
            "Call return type must be parameterized as Call<Foo>"
        }
        val innerType = getParameterUpperBound(0, returnType)

        return getApiResultType(innerType)
    }

    private fun getApiResultType(returnType: Type): Type? {
        if (getRawType(returnType) != Result::class.java) {
            return null
        }
        require(returnType is ParameterizedType &&
            getParameterUpperBound(1, returnType) == ApiError::class.java
        ) {
            "Call return type must be parameterized as ApiResult<Foo>"
        }
        return getParameterUpperBound(0, returnType)
    }

}

private class ApiResultCall<T>(
    private val call: Call<T>,
    private val resolver: ApiResultResolver
) : Call<ApiResult<T>> {

    override fun enqueue(callback: Callback<ApiResult<T>>) {
        call.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val apiResult = resolver.toApiResult(response, call)
                callback.onResponse(this@ApiResultCall, Response.success(apiResult))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val apiError = resolver.toApiError(t, error = null, call, response = null)
                callback.onResponse(this@ApiResultCall, Response.success(Err(apiError)))
            }
        })
    }

    override fun execute(): Response<ApiResult<T>> =
        Response.success(resolver.execute(call))

    override fun isExecuted(): Boolean = call.isExecuted

    override fun isCanceled(): Boolean = call.isCanceled

    override fun cancel() = call.cancel()

    override fun timeout(): Timeout = call.timeout()

    override fun clone(): Call<ApiResult<T>> = ApiResultCall(call.clone(), resolver)

    override fun request(): Request = call.request()
}