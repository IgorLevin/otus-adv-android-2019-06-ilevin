package ru.igor.levin.weatherforecast.data.dto.mapper

import ru.igor.levin.weatherforecast.data.dto.OpenWeatherResponse
import ru.igor.levin.weatherforecast.domain.entity.Weather

object WeatherResponseMapper {

    fun transformWeatherResponse(serviceResponse: OpenWeatherResponse) : Weather {

        val weather = Weather()

        weather.city = serviceResponse.name

        serviceResponse.weather?.let { weatherList ->
            if (weatherList.isNotEmpty()) {
                val w = weatherList[0]
                weather.description = w.description
                when(w.main) {
                    "Rain" -> {
                        weather.rain = serviceResponse.rain?.oneHour ?: serviceResponse.rain?.threeHours
                    }
                    "Snow" -> {
                        weather.snow = serviceResponse.show?.oneHour ?: serviceResponse.show?.threeHours
                    }
                }

            }
        }
        serviceResponse.main?.let { mainData ->
            weather.temperature = mainData.temp
            weather.humidity = mainData.humidity
            weather.pressure = mainData.pressure
        }
        serviceResponse.wind?.let { wind ->
            weather.windDirection = wind.deg
            weather.windSpeed = wind.speed
        }

        serviceResponse.clouds?.let {clouds ->
            weather.cloudness = clouds.all
        }
        return weather
    }
}