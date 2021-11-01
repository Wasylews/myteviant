package com.genius.wasylews.myteviant.sdk

import com.genius.wasylews.myteviant.common.network.ApiResult
import com.genius.wasylews.myteviant.sdk.remote.LoginResponse
import com.genius.wasylews.myteviant.sdk.remote.MyTeviantApi
import javax.inject.Inject

internal class TeviantRepository @Inject constructor(
    private val api: MyTeviantApi
) : TeviantApi {

    override suspend fun login(user: String, password: String): ApiResult<LoginResponse> =
        api.login(user, password)

}