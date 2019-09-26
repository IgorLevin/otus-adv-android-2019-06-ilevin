package ru.igor.levin.weatherforecast

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ProductionTree())
        }
    }

    private class ProductionTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            return
        }
    }
}