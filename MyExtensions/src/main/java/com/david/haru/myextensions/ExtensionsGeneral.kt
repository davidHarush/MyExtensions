package com.david.haru.myextensions

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.widget.Toast
import androidx.annotation.StringRes



val baseContext = ExtensionsApplication.extensionsContext
val Any.classTag: String
    get() {
        return this::class.java.simpleName
    }


fun getString(@StringRes string: Int): String = baseContext.resources.getString(string)

val Int.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


fun showToast(msg: String) {
    Toast.makeText(baseContext, msg.orEmpty(), Toast.LENGTH_LONG).show()
}

val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


val Context.powerManager: PowerManager
    get() = getSystemService(Context.POWER_SERVICE) as PowerManager

val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


@SuppressLint("MissingPermission")
fun Context.isNetworkConnected(): Boolean {
    try {
        // Need ACCESS_NETWORK_STATE permission
        val cManager = connectivityManager
        val network = cManager.activeNetwork
        if (network != null) {
            val networkCapabilities = cManager.getNetworkCapabilities(network)
            return networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            )
        }
        return false
    } catch (e: Exception) {
        logD(e.message.toString())
        return false
    }
}

fun doWithDelay(
    delay: Long = 500,
    action: () -> Unit
) {
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        action()
    }, delay)
}


//Returns true if no exception was caught. Otherwise, it logs the exception and returns false
fun Any.tryCatch(body: () -> Unit): Boolean {
    return try {
        body()
        true
    } catch (e: Throwable) {
        body.logE(e.message)
        false
    }
}
