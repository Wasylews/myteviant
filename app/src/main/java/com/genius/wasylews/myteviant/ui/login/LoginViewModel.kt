package com.genius.wasylews.myteviant.ui.login

import com.genius.wasylews.myteviant.common.ui.BaseViewModel
import com.genius.wasylews.myteviant.sdk.TeviantApi
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val repo: TeviantApi
) : BaseViewModel() {

    val loadingFlow = MutableStateFlow(false)
    val usernameFlow = MutableStateFlow("")
    val passwordFlow = MutableStateFlow("")
    val loginEnabledFLow = usernameFlow.combine(passwordFlow) { username, password ->
        username.isNotEmpty() && password.isNotEmpty()
    }

    fun login() {
        loadingFlow.trueWhileLaunch {
            repo.login(usernameFlow.value, passwordFlow.value)
                .onSuccess {
                    messageLiveData.value = it.status
                }
                .onFailure {
                    messageLiveData.value = it.message
                }
        }
    }
}