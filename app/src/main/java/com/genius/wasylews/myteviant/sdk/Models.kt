package com.genius.wasylews.myteviant.sdk

data class Profile(
    val login: String,
    val surname: String,
    val phone: String,
    val address: String,
    val contract: String,
    val contractDate: String,
)

data class Tariff(
    val name: String,
    val amount: String,
    val ip: String,
    val status: String,
)

data class PaymentHistory(
    val lastPaymentDate: String,
    val lastPaymentAmount: String,
    val nextPaymentDate: String
)