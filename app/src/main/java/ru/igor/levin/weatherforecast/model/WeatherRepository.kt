package ru.igor.levin.weatherforecast.model

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.igor.levin.weatherforecast.model.network.OpenWeatherApi
import ru.igor.levin.weatherforecast.model.network.OpenWeatherResponse
import java.util.*

class WeatherRepository(private val weatherApi: OpenWeatherApi) : WeatherModel {

    private val observable = ModelObservable()
    private var call: Call<OpenWeatherResponse.Result>? = null

    override fun getWeather(observer: Observer) {
        observable.addObserver(observer)
        observable.notify(WeatherModelResponse.Loading(), false)
        weatherApi
            .getWeatherByCityId()
            .enqueue(object : Callback<OpenWeatherResponse.Result> {
                override fun onFailure(call: Call<OpenWeatherResponse.Result>, t: Throwable) {
                    observable.notify(WeatherModelResponse.Error(t.localizedMessage))
                }

                override fun onResponse(
                    call: Call<OpenWeatherResponse.Result>,
                    response: Response<OpenWeatherResponse.Result>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body() as OpenWeatherResponse.Result

                        val data = WeatherModelData()

                        data.city = result.name

                        result.weather?.apply {
                            if (isNotEmpty()) {
                                val weather = get(0)
                                data.description = weather.description
                                when(weather.main) {
                                    "Rain" -> {
                                        data.rain = result.rain?.oneHour ?: result.rain?.threeHours
                                    }
                                    "Snow" -> {
                                        data.snow = result.show?.oneHour ?: result.show?.threeHours
                                    }
                                }

                            }
                        }
                        result.main?.apply {
                            data.temperature = temp
                            data.humidity = humidity
                            data.pressure = pressure
                        }
                        result.wind?.apply {
                            data.windDirection = deg
                            data.windSpeed = speed
                        }

                        result.clouds?.apply {
                            data.cloudness = all
                        }

                        observable.notify(WeatherModelResponse.Success(data))
                    } else {
                        observable.notify(WeatherModelResponse.Error("Getting weather error: ${response.message()}"))
                    }
                }
            })
    }

    override fun cancelRequest() {
        observable.deleteObservers()
        call?.cancel()
    }
}

class ModelObservable: Observable() {

    fun notify(result: Any, deleteObservers: Boolean = true) {
        setChanged()
        notifyObservers(result)
        if (deleteObservers) {
            deleteObservers()
        }
    }
}