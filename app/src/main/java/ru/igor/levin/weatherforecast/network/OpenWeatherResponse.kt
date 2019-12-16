package ru.igor.levin.weatherforecast.network

import androidx.annotation.Keep
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

@Keep
object OpenWeatherResponse {
    @Keep
    data class Result(
        val coord: Coordinates,
        val weather: List<Weather>,
        val base: String,           // Internal parameter
        val main: Main,
        val visibility: String,
        val wind: Wind,
        val clouds: Clouds,
        val rain: Rain,
        val show: Snow,
        val dt: String,             // Time of data calculation, unix, UTC
        val sys: Sys,
        val timezone: String,       // Shift in seconds from UTC
        val id: String,             // City id
        val name: String,           // City name
        val cod: String)            // Internal parameter
    {
        override fun toString(): String {
            return GsonBuilder().setPrettyPrinting().create().toJson(this)
        }
    }

    @Keep
    data class Coordinates(val lon: String, val lat: String)
    @Keep
    data class Weather(
        val id: String,             // Weather condition id
        val main: String,           // Group of weather parameters (Rain, Snow, Extreme etc.)
        val description: String,    // Weather condition within the group
        val icon: String)           // Weather icon id
    @Keep
    data class Main(
        val temp: String,           // Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        val pressure: String,       // Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
        val humidity: String,       // Humidity, %
        val temp_min: String,       // Minimum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        val temp_max: String,       // Maximum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        val sea_level: String,      // Atmospheric pressure on the sea level, hPa
        val grnd_level: String      // Atmospheric pressure on the ground level, hPa
    )
    @Keep
    data class Wind(
        val speed: String,          // Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
        val deg: String             // Wind direction, degrees (meteorological)
    )
    @Keep
    data class Clouds(
        val all: String             // Cloudiness, %
    )
    @Keep
    data class Rain (
        @SerializedName("1h")
        val one_hour: String,       // Rain volume for the last 1 hour, mm
        @SerializedName("3h")
        val three_hours: String     // Rain volume for the last 3 hours, mm
    )
    @Keep
    data class Snow (
        @SerializedName("1h")
        val one_hour: String,       // Snow volume for the last 1 hour, mm
        @SerializedName("3h")
        val three_hours: String     // Snow volume for the last 3 hours, mm
    )
    @Keep
    data class Sys(
        val type: String,           // Internal parameter
        val id: String,             // Internal parameter
        val message: String,        // Internal parameter
        val country: String,        // Country code (GB, JP etc.)
        val sunrise: String,        // Sunrise time, unix, UTC
        val sunset: String          // Sunset time, unix, UTC
    )
}
