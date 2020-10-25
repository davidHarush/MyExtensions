package com.david.haru.myextensions

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes


fun getBaseApp() = BaseApplication.instance
fun getBaseAppContext() = BaseApplication.applicationContext

fun getString(@StringRes string: Int): String = getBaseAppContext().resources.getString(string)

val Int.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


fun showToast(msg: String) {
    Toast.makeText(getBaseAppContext(), msg.orEmpty(), Toast.LENGTH_LONG).show()
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


fun Any.logD(message: String?) {
    Log.d(this::class.java.simpleName, message.orEmpty())
}

fun Any.logI(message: String?) {
    Log.i(this::class.java.simpleName, message.orEmpty())
}


fun Any.logE(message: String?, e: Throwable = Throwable("empty")) {
    if (e.message == "empty") {
        Log.e(this::class.java.toString(), "" + message.orEmpty())
    } else {
        logException(e)
    }
}

fun Any.logException(e: Throwable) {
    Log.e(this::class.java.toString(), "" + e.message.toString(), e)
}

//Returns true if no exception was caught. Otherwise, it logs the exception and returns false
fun tryCatch(body: () -> Unit): Boolean {
    return try {
        body()
        true
    } catch (e: Throwable) {
        body.logE(e.message)
        false
    }
}
