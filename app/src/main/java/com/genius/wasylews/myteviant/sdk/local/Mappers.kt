package com.genius.wasylews.myteviant.sdk.local

import com.genius.wasylews.myteviant.sdk.PaymentHistory
import com.genius.wasylews.myteviant.sdk.Profile
import com.genius.wasylews.myteviant.sdk.Tariff

internal fun Profile.toProfileEntity() = ProfileEntity (
    login = this.login,
    surname = this.surname,
    phone = this.phone,
    address = this.address,
    contract = this.contract,
    contractDate = this.contractDate
)

internal fun ProfileEntity.toProfile() = Profile(
    login = this.login,
    surname = this.surname,
    phone = this.phone,
    address = this.address,
    contract = this.contract,
    contractDate = this.contractDate
)

internal fun Tariff.toTariffEntity() = TariffEntity(
    name = this.name,
    amount = this.amount,
    ip = this.ip,
    status = this.status
)

internal fun TariffEntity.toTariff() = Tariff(
    name = this.name,
    amount = this.amount,
    ip = this.ip,
    status = this.status
)

internal fun PaymentHistory.toPaymentHistoryEntity() = PaymentHistoryEntity(
    lastPaymentDate = this.lastPaymentDate,
    lastPaymentAmount = this.lastPaymentAmount,
    nextPaymentDate = this.nextPaymentDate
)

internal fun PaymentHistoryEntity.toPaymentHistory() = PaymentHistory(
    lastPaymentDate = this.lastPaymentDate,
    lastPaymentAmount = this.lastPaymentAmount,
    nextPaymentDate = this.nextPaymentDate
)