package com.genius.wasylews.myteviant.ui.login

import com.genius.wasylews.myteviant.common.ui.BaseViewModel
import com.genius.wasylews.myteviant.sdk.TeviantApi
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val repo: TeviantApi
) : BaseViewModel() {

    fun login(username: String, password: String) {
        coroutineScope.launch {
            repo.login(username, password)
                .onSuccess {

                }
                .onFailure {
                    messageLiveData.value = it.message
                }
        }
    }
}