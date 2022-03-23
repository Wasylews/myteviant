package com.genius.wasylews.myteviant.sdk.remote

import com.genius.wasylews.myteviant.sdk.PaymentHistory
import com.genius.wasylews.myteviant.sdk.Profile
import com.genius.wasylews.myteviant.sdk.Tariff

internal fun ProfileResponse.toProfile() = Profile(
    login = this.login,
    surname = this.surname,
    phone = this.phone,
    address = this.address,
    contract = this.contract,
    contractDate = this.contractDate,
)

internal fun MainResponse.toTariff() = Tariff(
    name = this.tariff.tariff,
    amount = this.tariff.tariffAmount,
    ip = this.ip,
    status = this.status,
)

internal fun PaymentHistoryResponse.toPaymentHistory() = PaymentHistory(
    lastPaymentDate = this.lastPaymentDate,
    lastPaymentAmount = this.lastPaymentAmount,
    nextPaymentDate = this.nextPayment
)