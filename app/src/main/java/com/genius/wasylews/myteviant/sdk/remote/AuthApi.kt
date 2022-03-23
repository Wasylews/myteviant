package com.genius.wasylews.myteviant.sdk.remote

import com.genius.wasylews.myteviant.common.network.ApiResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface AuthApi {

    @POST("index.cgi")
    @FormUrlEncoded
    suspend fun login(
        @Field("user") user: String,
        @Field("passwd") password: String
    ): ApiResult<MainResponse>
}