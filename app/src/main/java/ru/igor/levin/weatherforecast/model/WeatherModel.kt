package ru.igor.levin.weatherforecast.model

import java.util.*

interface WeatherModel {
    fun getWeather(observer: Observer)
    fun cancelRequest()
}