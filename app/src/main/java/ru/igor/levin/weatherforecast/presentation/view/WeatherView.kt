package ru.igor.levin.weatherforecast.presentation.view

interface WeatherView : BaseView {
    fun showWeatherScreen()
    fun showDescription(info: String)
    fun showCity(name: String)
    fun showClouds(percent: String)
    fun showTemperature(degrees: String)
    fun showHumidity(percent: String)
    fun showPressure(mm: String)
    fun showWind(direction: String)
    fun showPrecipitation(levelInMm: String)
    fun hidePrecipitation()
}