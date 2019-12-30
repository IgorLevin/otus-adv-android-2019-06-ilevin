package ru.igor.levin.weatherforecast.presenter

import ru.igor.levin.weatherforecast.view.WeatherView

interface WeatherPresenter {

    fun onViewActivated(view: WeatherView)
    fun onViewDeactivated()
    fun onViewDestroyed()

    fun getWeather()
}