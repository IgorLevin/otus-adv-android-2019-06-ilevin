package ru.igor.levin.weatherforecast.model.network

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

object OpenWeatherResponse {

    data class Result(
        var coord: Coordinates? = null,
        var weather: List<Weather>? = null,
        var base: String? = null,           // Internal parameter
        var main: Main? = null,
        var visibility: String? = null,
        var wind: Wind? = null,
        var clouds: Clouds? = null,
        var rain: Rain? = null,
        var show: Snow? = null,
        var dt: String? = null,             // Time of data calculation, unix, UTC
        var sys: Sys? = null,
        var timezone: String? = null,       // Shift in seconds from UTC
        var id: String? = null,             // City id
        var name: String? = null,           // City name
        var cod: String? = null)            // Internal parameter
    {
        override fun toString(): String {
            return GsonBuilder().setPrettyPrinting().create().toJson(this)
        }
    }

    data class Coordinates(
        var lon: String? = null,
        var lat: String? = null
    )

    data class Weather(
        var id: String? = null,             // Weather condition id
        var main: String? = null,           // Group of weather parameters (Rain, Snow, Extreme etc.)
        var description: String? = null,    // Weather condition within the group
        var icon: String? = null            // Weather icon id
    )

    data class Main(
        var temp: String? = null,           // Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        var pressure: String? = null,       // Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
        var humidity: String? = null,       // Humidity, %
        var temp_min: String? = null,       // Minimum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        var temp_max: String? = null,       // Maximum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        var sea_level: String? = null,      // Atmospheric pressure on the sea level, hPa
        var grnd_level: String? = null      // Atmospheric pressure on the ground level, hPa
    )

    data class Wind(
        var speed: String? = null,          // Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
        var deg: String? = null             // Wind direction, degrees (meteorological)
    )

    data class Clouds(
        var all: String? = null             // Cloudiness? = null, %
    )

    data class Rain (
        @SerializedName("1h")
        var one_hour: String? = null,       // Rain volume for the last 1 hour, mm
        @SerializedName("3h")
        var three_hours: String? = null     // Rain volume for the last 3 hours, mm
    )

    data class Snow (
        @SerializedName("1h")
        var one_hour: String? = null,       // Snow volume for the last 1 hour, mm
        @SerializedName("3h")
        var three_hours: String? = null     // Snow volume for the last 3 hours, mm
    )

    data class Sys(
        var type: String? = null,           // Internal parameter
        var id: String? = null,             // Internal parameter
        var message: String? = null,        // Internal parameter
        var country: String? = null,        // Country code (GB, JP etc.)
        var sunrise: String? = null,        // Sunrise time, unix, UTC
        var sunset: String? = null          // Sunset time, unix, UTC
    )
}
