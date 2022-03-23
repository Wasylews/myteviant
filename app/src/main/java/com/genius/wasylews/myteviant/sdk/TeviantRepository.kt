package com.genius.wasylews.myteviant.sdk

import com.genius.wasylews.myteviant.common.network.ApiResult
import com.genius.wasylews.myteviant.sdk.local.LocalStorage
import com.genius.wasylews.myteviant.sdk.remote.AuthApi
import com.genius.wasylews.myteviant.sdk.remote.toPaymentHistory
import com.genius.wasylews.myteviant.sdk.remote.toProfile
import com.genius.wasylews.myteviant.sdk.remote.toTariff
import com.github.michaelbull.result.map
import kotlinx.coroutines.flow.Flow
import okhttp3.Cookie
import okhttp3.HttpUrl
import javax.inject.Inject

internal class TeviantRepository @Inject constructor(
    private val authApi: AuthApi,
    private val localStorage: LocalStorage
) : TeviantApi {

    override suspend fun login(user: String, password: String): ApiResult<Unit> =
        authApi.login(user, password)
            .map {
                localStorage.saveProfile(it.profile.toProfile())
                localStorage.saveTariff(it.toTariff())
                localStorage.savePaymentHistory(it.paymentHistory.toPaymentHistory())
            }

    override fun getProfileFlow(): Flow<Profile> =
        localStorage.getProfileFlow()

    override fun getTariffFlow(): Flow<Tariff> =
        localStorage.getTariffFlow()

    override fun getPaymentHistoryFlow(): Flow<PaymentHistory> =
        localStorage.getPaymentHistoryFlow()

    override fun getAuthCookie(url: HttpUrl): Cookie? =
        localStorage.getAuthCookie(url)

    override fun saveAuthCookie(cookie: Cookie) =
        localStorage.saveAuthCookie(cookie)
}