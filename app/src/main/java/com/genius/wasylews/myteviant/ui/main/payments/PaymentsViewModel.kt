package com.genius.wasylews.myteviant.ui.main.payments

import com.genius.wasylews.myteviant.common.ui.BaseViewModel
import com.genius.wasylews.myteviant.common.ui.SingleLiveEvent
import com.genius.wasylews.myteviant.sdk.TeviantApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
internal class PaymentsViewModel @Inject constructor(
    repo: TeviantApi
) : BaseViewModel() {

    val loadingFlow = MutableStateFlow(false)
    val stateFlow = repo.getTariffFlow()
        .combine(repo.getPaymentHistoryFlow()) { tariff, history ->
            StateUiModel(
                tariff = tariff.name,
                payment = tariff.amount,
                status = tariff.status,
                nextPayment = history.nextPaymentDate
            )
    }
    val navToAuthLiveEvent = SingleLiveEvent<Void>()
}