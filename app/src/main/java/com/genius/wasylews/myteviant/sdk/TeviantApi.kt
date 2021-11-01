package com.genius.wasylews.myteviant.sdk

import com.genius.wasylews.myteviant.common.network.ApiResult
import com.genius.wasylews.myteviant.sdk.remote.LoginResponse

internal interface TeviantApi {

    suspend fun login(user: String, password: String): ApiResult<LoginResponse>
}