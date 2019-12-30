package ru.igor.levin.weatherforecast.model

import java.util.*

data class WeatherModelData(
    var city: String? = null,
    var cloudness: String? = null,
    var description: String? = null,
    var temperature: String? = null,
    var pressure: String? = null,
    var humidity: String? = null,
    var windSpeed: String? = null,
    var windDirection: String? = null,
    var rain: String? = null,
    var snow: String? = null
) : Observable()


sealed class WeatherModelResponse<T>(val data: T? = null, val msg: String? = null) {
    class Success(data: WeatherModelData): WeatherModelResponse<WeatherModelData>(data, null)
    class Error(info: String? = null): WeatherModelResponse<WeatherModelData>(null, info)
    class Loading(): WeatherModelResponse<WeatherModelData>()
}