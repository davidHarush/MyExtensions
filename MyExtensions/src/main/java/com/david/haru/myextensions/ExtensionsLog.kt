package com.david.haru.myextensions

import android.util.Log

fun Any.logD(message: String?) =
    Log.d(classTag, message.orEmpty())


fun Any.logI(message: String?) =
    Log.i(classTag, message.orEmpty())


fun Any.logE(message: String?, e: Throwable = Throwable("empty")) {
    if (e.message == "empty") {
        Log.e(classTag, "" + message.orEmpty())
    } else {
        logException(e)
    }
}

fun Any.logException(e: Throwable) =
    Log.e(classTag, "" + e.message.toString(), e)

