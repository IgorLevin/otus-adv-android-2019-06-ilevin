package ru.igor.levin.weatherforecast.domain.entity

data class Weather(
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
)