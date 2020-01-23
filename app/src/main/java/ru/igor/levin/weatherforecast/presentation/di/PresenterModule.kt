package ru.igor.levin.weatherforecast.presentation.di

import dagger.Binds
import dagger.Module
import ru.igor.levin.weatherforecast.presentation.presenter.WeatherPresenter
import ru.igor.levin.weatherforecast.presentation.presenter.WeatherPresenterImpl

@Module
abstract class PresenterModule {
    @Binds
    abstract fun getPresenter(presenter: WeatherPresenterImpl): WeatherPresenter
}