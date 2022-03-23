package com.genius.wasylews.myteviant.sdk.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
internal data class ProfileEntity(
    @PrimaryKey val login: String,
    val surname: String,
    val phone: String,
    val address: String,
    val contract: String,
    @ColumnInfo(name = "contract_date") val contractDate: String,
)

@Entity(tableName = "tariffs")
internal data class TariffEntity(
    @PrimaryKey val name: String,
    val amount: String,
    val ip: String,
    val status: String,
)

@Entity(tableName = "payments")
internal data class PaymentHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "last_payment_date") val lastPaymentDate: String,
    @ColumnInfo(name = "last_payment_amount") val lastPaymentAmount: String,
    @ColumnInfo(name = "next_payment_date") val nextPaymentDate: String
)
