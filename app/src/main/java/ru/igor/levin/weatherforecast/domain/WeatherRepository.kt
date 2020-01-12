package ru.igor.levin.weatherforecast.domain

import java.util.*

interface WeatherRepository {
    fun getWeather(observer: Observer)
    fun cancelRequest()
}