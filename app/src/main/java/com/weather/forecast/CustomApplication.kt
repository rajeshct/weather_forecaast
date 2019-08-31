package com.weather.forecast

import android.app.Application
import com.weather.forecast.utils.dependency.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidLogger()
            androidContext(this@CustomApplication)
            modules(appComponent)
        }
    }

}