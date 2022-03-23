package com.genius.wasylews.myteviant.common.network

import com.genius.wasylews.myteviant.sdk.TeviantApi
import dagger.Lazy
import okhttp3.Cookie
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

internal const val AUTH_COOKIE_HEADER = "Cookie"
internal const val AUTH_SET_COOKIE_HEADER = "Set-Cookie"

internal class AuthInterceptor @Inject constructor(
    private val repo: Lazy<TeviantApi>
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        if (chain.request().header(AUTH_COOKIE_HEADER) == null) {
            val authCookie = repo.get().getAuthCookie(chain.request().url)
            if (authCookie != null) {
                requestBuilder.addHeader(AUTH_COOKIE_HEADER,
                    "${authCookie.name}=${authCookie.value}")
            } else {
                return Response.Builder()
                    .code(401)
                    .protocol(Protocol.HTTP_1_1)
                    .request(chain.request())
                    .message("Unauthorized")
                    .body("User unauthorized".toResponseBody())
                    .build()
            }
        }

        val response = chain.proceed(requestBuilder.build())
        response.header(AUTH_SET_COOKIE_HEADER)?.let {
            Cookie.parse(response.request.url, it)?.let { cookie ->
                repo.get().saveAuthCookie(cookie)
            }
        }

        return response
    }
}

internal class AuthCookieInterceptor @Inject constructor(
    private val repo: Lazy<TeviantApi>
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        response.header(AUTH_SET_COOKIE_HEADER)?.let {
            Cookie.parse(response.request.url, it)?.let { cookie ->
                repo.get().saveAuthCookie(cookie)
            }
        }

        return response
    }
}