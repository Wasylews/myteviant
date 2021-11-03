package com.genius.wasylews.myteviant.sdk.remote

import pl.droidsonroids.jspoon.annotation.Selector

internal class LoginResponse {
    @Selector(".table")
    lateinit var profile: ProfileResponse

    @Selector("ul.nav:nth-child(2) > li:nth-child(3) > a:nth-child(1) > span", regex="IP: (.+)")
    lateinit var ip: String

    @Selector("div.row:nth-child(12) > div:nth-child(2)")
    lateinit var status: String

    @Selector(".box-body")
    lateinit var tariff: TariffResponse

    @Selector(".table")
    lateinit var payment: PaymentResponse
}

internal class ProfileResponse {
    @Selector("div:nth-child(1) > div:nth-child(2)", regex="(\\d+)")
    lateinit var login: String

    @Selector("div:nth-child(6) > div:nth-child(2)")
    lateinit var surname: String

    @Selector("div:nth-child(7) > div:nth-child(2)")
    lateinit var phone: String

    @Selector("div:nth-child(8) > div:nth-child(2)")
    lateinit var address: String

    @Selector("div:nth-child(10) > div:nth-child(2)")
    lateinit var contract: String

    @Selector("div:nth-child(11) > div:nth-child(2)")
    lateinit var contractDate: String
}

internal class TariffResponse {
    @Selector("div:nth-child(3) > div:nth-child(2)")
    lateinit var tariff: String

    @Selector("div:nth-child(4) > div:nth-child(2)")
    lateinit var tariffAmount: String
}

internal class PaymentResponse {
    @Selector("div.row:nth-child(14) > div:nth-child(2)")
    lateinit var lastPaymentDate: String

    @Selector("div.row:nth-child(15) > div:nth-child(2)")
    lateinit var lastPaymentAmount: String

    @Selector(".callout", regex="\\((.+)\\)")
    lateinit var nextPayment: String
}