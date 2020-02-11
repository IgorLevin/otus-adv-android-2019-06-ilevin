package ru.igor.levin.weatherforecast

import android.app.Application
import ru.igor.levin.weatherforecast.di.AppComponent
import ru.igor.levin.weatherforecast.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

class WeatherApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}