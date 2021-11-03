package com.genius.wasylews.myteviant.common.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genius.wasylews.myteviant.BuildConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

internal abstract class BaseViewModel: ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

        if (BuildConfig.DEBUG) {
            throw throwable
        } else {
            throwable.printStackTrace()
        }
    }

    protected val coroutineScope = viewModelScope + exceptionHandler
    val messageLiveData = SingleLiveEvent<String>()

    fun MutableStateFlow<Boolean>.trueWhileLaunch(
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
        block: suspend () -> Unit
    ): Job =
        coroutineScope.launch(coroutineContext) {
            trueWhile { block() }
        }
}