package ru.igor.levin.weatherforecast.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.igor.levin.weatherforecast.data.dto.OpenWeatherResponse
import ru.igor.levin.weatherforecast.data.dto.mapper.WeatherResponseMapper
import ru.igor.levin.weatherforecast.data.network.OpenWeatherApi
import ru.igor.levin.weatherforecast.domain.WeatherLoadingState
import ru.igor.levin.weatherforecast.domain.WeatherRepository
import java.util.*

class WeatherRepositoryImpl(private val weatherApi: OpenWeatherApi)
    : WeatherRepository
{
    private val observable = ModelObservable()
    private var call: Call<OpenWeatherResponse>? = null

    override fun getWeather(observer: Observer) {
        observable.addObserver(observer)
        observable.notify(WeatherLoadingState.Loading(), false)
        weatherApi
            .getWeatherByCityId()
            .enqueue(object : Callback<OpenWeatherResponse> {
                override fun onFailure(call: Call<OpenWeatherResponse>, t: Throwable) {
                    observable.notify(
                        WeatherLoadingState.Error(
                            t.localizedMessage
                        )
                    )
                }

                override fun onResponse(
                    call: Call<OpenWeatherResponse>,
                    response: Response<OpenWeatherResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val result = response.body()!!
                        val data = WeatherResponseMapper.transformWeatherResponse(result)

                        observable.notify(
                            WeatherLoadingState.Success(data)
                        )
                    } else {
                        observable.notify(
                            WeatherLoadingState.Error(
                                "Getting weather error: ${response.message()}"
                            )
                        )
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