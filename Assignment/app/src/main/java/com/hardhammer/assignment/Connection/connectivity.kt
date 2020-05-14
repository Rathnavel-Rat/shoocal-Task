package com.hardhammer.assignment.Connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.getSystemService

class connectionManager {
    fun checkConnection(context: Context):Boolean{
        val net= context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo=net.activeNetworkInfo
        return activeNetworkInfo.isConnected
    }
}