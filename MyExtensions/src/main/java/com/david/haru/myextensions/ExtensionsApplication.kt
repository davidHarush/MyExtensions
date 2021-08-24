package com.david.haru.myextensions

import android.app.Application
import android.content.Context

open class ExtensionsApplication : Application() {


    companion object {
        lateinit internal var extensionsContext: Context
            private set

    }



    override fun onCreate() {
        super.onCreate()
        extensionsContext = baseContext
    }
}
