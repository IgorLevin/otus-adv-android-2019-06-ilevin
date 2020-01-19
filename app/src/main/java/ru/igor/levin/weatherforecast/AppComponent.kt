package ru.igor.levin.weatherforecast

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [AppModule::class])
interface AppComponent {
    fun inject(app: WeatherApplication)
}