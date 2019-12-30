package ru.igor.levin.weatherforecast.view

interface WeatherView : BaseView {
    fun showWeatherScreen()
    fun showDescription(info: String)
    fun showCity(name: String)
    fun showCloudness(percent: String)
    fun showTemperature(degrees: String)
    fun showHumidity(percent: String)
    fun showPressure(mm: String)
    fun showWind(info: String)
    fun showPrecipitation(info: String)
    fun hidePrecipitation()
}