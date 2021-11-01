package com.genius.wasylews.myteviant.sdk.remote

import com.genius.wasylews.myteviant.common.ApiResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface MyTeviantApi {

    @POST("index.cgi")
    @FormUrlEncoded
    suspend fun login(
        @Field("user") user: String,
        @Field("passwd") password: String
    ): ApiResult<LoginResponse>
}