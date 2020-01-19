package ru.igor.levin.weatherforecast.domain.interactor

import ru.igor.levin.weatherforecast.domain.WeatherLoadingState
import ru.igor.levin.weatherforecast.domain.entity.Weather

interface WeatherInteractor {

    fun subscribe(listener: Listener)

    fun unsubscribe()

    fun updateWeather()

    interface Listener {
        fun onWeatherLoadingStateChanged(state: WeatherLoadingState<Weather>)
    }
}