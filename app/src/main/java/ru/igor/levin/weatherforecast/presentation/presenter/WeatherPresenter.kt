package ru.igor.levin.weatherforecast.presentation.presenter

import ru.igor.levin.weatherforecast.presentation.view.WeatherView

interface WeatherPresenter {

    fun onViewActivated(view: WeatherView)
    fun onViewDeactivated()
    fun onViewDestroyed()

    fun updateWeather()
}