package com.genius.wasylews.myteviant.sdk.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProfileEntity::class, TariffEntity::class, PaymentHistoryEntity::class],
    version = 1
)
internal abstract class AppDatabase: RoomDatabase() {

    abstract val profileDao: ProfileDao
    abstract val tariffDao: TariffDao
    abstract val paymentHistoryDao: PaymentHistoryDao
}