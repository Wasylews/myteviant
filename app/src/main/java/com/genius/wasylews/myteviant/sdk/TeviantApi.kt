package com.genius.wasylews.myteviant.sdk

import com.genius.wasylews.myteviant.common.network.ApiResult
import kotlinx.coroutines.flow.Flow
import okhttp3.Cookie
import okhttp3.HttpUrl

internal interface TeviantApi {

    suspend fun login(user: String, password: String): ApiResult<Unit>

    fun getProfileFlow(): Flow<Profile>

    fun getTariffFlow(): Flow<Tariff>

    fun getPaymentHistoryFlow(): Flow<PaymentHistory>

    fun getAuthCookie(url: HttpUrl): Cookie?

    fun saveAuthCookie(cookie: Cookie)
}