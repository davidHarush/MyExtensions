package com.david.haru.myextensions

import android.app.Application
import android.content.Context

open class BaseApplication : Application() {


    companion object {
        lateinit var baseContext: Context
            private set

    }



    override fun onCreate() {
        super.onCreate()
        baseContext = baseContext
    }
}
