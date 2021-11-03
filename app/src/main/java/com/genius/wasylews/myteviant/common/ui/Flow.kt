package com.genius.wasylews.myteviant.common.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

fun <T> Fragment.observe(flow: Flow<T>, onChanged: (T) -> Unit) {
    flow.asLiveData(timeoutInMs = 0)
        .observe(viewLifecycleOwner, onChanged)
}

fun <T> Fragment.observe(liveData: LiveData<T>, onChanged: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, onChanged)
}

inline fun <R> MutableStateFlow<Boolean>.trueWhile(block: () -> R) =
    try {
        value = true
        block()
    } finally {
        value = false
    }