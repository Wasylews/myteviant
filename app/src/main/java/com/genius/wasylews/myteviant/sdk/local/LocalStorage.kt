package com.genius.wasylews.myteviant.sdk.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.genius.wasylews.myteviant.sdk.PaymentHistory
import com.genius.wasylews.myteviant.sdk.Profile
import com.genius.wasylews.myteviant.sdk.Tariff
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.Cookie
import okhttp3.HttpUrl
import javax.inject.Inject
private const val KEY_AUTH_COOKIE = "auth_cookie"

internal class LocalStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val db: AppDatabase
) {
    fun getAuthCookie(url: HttpUrl): Cookie? {
        return sharedPreferences.getString(KEY_AUTH_COOKIE, null)?.let {
            Cookie.parse(url, it)?.let { cookie ->
                cookie.takeIf {
                    cookie.expiresAt > System.currentTimeMillis()
                }
            }
        }
    }

    fun saveAuthCookie(cookie: Cookie) {
        sharedPreferences.edit(commit = true) {
            putString(KEY_AUTH_COOKIE, cookie.toString())
        }
    }

    suspend fun saveProfile(profile: Profile) =
        db.profileDao.saveProfile(profile.toProfileEntity())

    fun getProfileFlow(): Flow<Profile> =
        db.profileDao.getProfileFlow().map { it.toProfile() }

    suspend fun saveTariff(tariff: Tariff) =
        db.tariffDao.saveTariff(tariff.toTariffEntity())

    fun getTariffFlow(): Flow<Tariff> =
        db.tariffDao.getTariffFlow().map { it.toTariff() }

    suspend fun savePaymentHistory(history: PaymentHistory) =
        db.paymentHistoryDao.savePaymentHistory(history.toPaymentHistoryEntity())

    fun getPaymentHistoryFlow(): Flow<PaymentHistory> =
        db.paymentHistoryDao.getPaymentHistoryFlow().map { it.toPaymentHistory() }
}