package com.david.haru.myextensions

import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


fun Context.getApp() = applicationContext as BaseApplication
fun Fragment.getApp() = context!!.applicationContext as BaseApplication
fun getApp() = BaseApplication.instance
fun getAppContext() = BaseApplication.applicationContext

@Suppress("DEPRECATION")
fun getColor(@ColorRes color: Int) = getAppContext().resources.getColor(color)

fun getString(@StringRes string: Int): String = getAppContext().resources.getString(string)

val Int.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


fun showToast(msg: String) {
    Toast.makeText(getAppContext(), msg.orEmpty(), Toast.LENGTH_LONG).show()
}

fun Context.showToast(@StringRes resource: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resource, duration).show()
}

fun Context.showToast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text.orEmpty(), duration).show()
}


val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

val Context.powerManager: PowerManager
    get() = getSystemService(Context.POWER_SERVICE) as PowerManager


fun doWithDelay(
    delay: Long = 500,
    action: () -> Unit
) {
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        action()
    }, delay)
}

fun Any.logD(message: String) {
    Log.d(this::class.java.simpleName, message.orEmpty())
}

fun Any.logE(message: String? , e: Throwable = Throwable("empty")) {
    if (e.message == "empty") {
        Log.e(this::class.java.toString(), "" + message.orEmpty())
    } else {
        Log.e(this::class.java.toString(), "" + message.orEmpty(), e)
    }
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
