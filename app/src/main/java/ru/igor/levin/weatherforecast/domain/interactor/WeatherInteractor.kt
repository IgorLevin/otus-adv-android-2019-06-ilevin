package ru.igor.levin.weatherforecast.domain.interactor

import ru.igor.levin.weatherforecast.domain.WeatherLoadingState
import ru.igor.levin.weatherforecast.domain.WeatherRepository
import ru.igor.levin.weatherforecast.domain.entity.Weather
import java.util.*

class WeatherInteractor (
    private val repository: WeatherRepository
)
{
    private var listener: Listener? = null

    private val observer = Observer { _, arg ->
        listener?.onWeatherLoadingStateChanged(arg as WeatherLoadingState<Weather>)
    }

    fun subscribe(listener: Listener) {
        this.listener = listener
        repository.getWeather(observer)
    }

    fun unsubscribe() {
        this.listener = null
        repository.cancelRequest()
    }

    fun updateWeather() {
        repository.getWeather(observer)
    }

    interface Listener {
        fun onWeatherLoadingStateChanged(state: WeatherLoadingState<Weather>)
    }
}