package com.smart.task

import android.app.Application

class SmartTaskApplication : Application() {

    companion object {
        lateinit var instance: SmartTaskApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}