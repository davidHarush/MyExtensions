package com.david.haru.myextensions

import android.app.Application
import android.content.Context

open class BaseApplication : Application() {


    companion object {
        lateinit var instance: BaseApplication
            private set

        val applicationContext: Context
            get() = instance.applicationContext

    }



    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
