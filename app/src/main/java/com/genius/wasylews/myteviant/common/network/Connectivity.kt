package com.genius.wasylews.myteviant.common.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET

interface Connectivity {
    val isOnline: Boolean
    val isOffline: Boolean
}

class ConnectivityImpl(context: Context) : Connectivity {

    private val connectivityManager = context.connectivityManager

    override val isOnline: Boolean
        get() = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?.hasCapability(NET_CAPABILITY_INTERNET) == true

    override val isOffline: Boolean get() = !isOnline
}

val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager