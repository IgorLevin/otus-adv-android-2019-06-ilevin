package ru.igor.levin.weatherforecast.domain.interactor

import ru.igor.levin.weatherforecast.domain.WeatherLoadingState
import ru.igor.levin.weatherforecast.domain.WeatherRepository
import ru.igor.levin.weatherforecast.domain.entity.Weather
import java.util.*

class WeatherInteractorImpl (
    private val repository: WeatherRepository
) : WeatherInteractor
{
    private var listener: WeatherInteractor.Listener? = null

    private val observer = Observer { _, arg ->
        listener?.onWeatherLoadingStateChanged(arg as WeatherLoadingState<Weather>)
    }

    override fun subscribe(listener: WeatherInteractor.Listener) {
        this.listener = listener
        repository.getWeather(observer)
    }

    override fun unsubscribe() {
        this.listener = null
        repository.cancelRequest()
    }

    override fun updateWeather() {
        repository.getWeather(observer)
    }
}