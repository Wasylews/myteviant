package com.genius.wasylews.myteviant.sdk.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProfileDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveProfile(profile: ProfileEntity)

    @Query("SELECT * FROM profiles LIMIT 1")
    fun getProfileFlow(): Flow<ProfileEntity>
}

@Dao
internal interface TariffDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveTariff(tariff: TariffEntity)

    @Query("SELECT * FROM tariffs LIMIT 1")
    fun getTariffFlow(): Flow<TariffEntity>
}

@Dao
internal interface PaymentHistoryDao {

    @Insert(onConflict = REPLACE)
    suspend fun savePaymentHistory(history: PaymentHistoryEntity)

    @Query("SELECT * FROM payments LIMIT 1")
    fun getPaymentHistoryFlow(): Flow<PaymentHistoryEntity>
}