package ru.igor.levin.weatherforecast.data.di

import dagger.Binds
import dagger.Module
import ru.igor.levin.weatherforecast.data.WeatherRepositoryImpl
import ru.igor.levin.weatherforecast.domain.WeatherRepository

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideWeatherRepository(repo: WeatherRepositoryImpl): WeatherRepository
}