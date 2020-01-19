package ru.igor.levin.weatherforecast

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (private val app: WeatherApplication) {

    @Provides
    @Singleton
    fun provideApp(): WeatherApplication = app
}