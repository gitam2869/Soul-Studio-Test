package com.app.soulstudio.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.app.soulstudio.common.SoulStudioApplication

class NetworkUtility {

    companion object {
        fun isInternetAvailable(): Boolean {
            var connected = false
            try {
                val connectivityManager = SoulStudioApplication.applicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                connected =
                    (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED
                            || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED)
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
            return connected
        }
    }
}