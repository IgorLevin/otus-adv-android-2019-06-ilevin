package ru.igor.levin.weatherforecast.domain

import ru.igor.levin.weatherforecast.domain.entity.Weather

sealed class WeatherLoadingState<T>(val data: T? = null, val msg: String? = null) {
    class Success(data: Weather): WeatherLoadingState<Weather>(data, null)
    class Error(info: String? = null): WeatherLoadingState<Weather>(null, info)
    class Loading: WeatherLoadingState<Weather>()
}