package com.genius.wasylews.myteviant.common.network

typealias Result<T, E> = com.github.michaelbull.result.Result<T, E>
typealias Some<T> = Result<T, Throwable>

inline fun <V> tryCatch(block: () -> V): Some<V> =
    com.github.michaelbull.result.runCatching(block)
