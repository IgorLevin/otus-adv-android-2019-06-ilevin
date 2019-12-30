package ru.igor.levin.weatherforecast

import android.app.Application
import android.content.Context
import ru.igor.levin.weatherforecast.model.WeatherModel
import ru.igor.levin.weatherforecast.model.WeatherRepository
import ru.igor.levin.weatherforecast.model.network.NetworkService
import ru.igor.levin.weatherforecast.model.network.OpenWeatherApi
import ru.igor.levin.weatherforecast.presenter.WeatherPresenter
import ru.igor.levin.weatherforecast.presenter.WeatherPresenterImpl

class ServiceLocator private constructor(val app: Application){

    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null

        fun instance(context: Context): ServiceLocator {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = ServiceLocator(app = context.applicationContext as Application)
                    }
                }
            }
            return instance!!
        }
    }
    private val api by lazy {
        NetworkService.instance.getOpenWeatherApi()
    }

    private val model by lazy {
        WeatherRepository(api)
    }

    private val presenter by lazy {
        WeatherPresenterImpl(model)
    }

    fun getWeatherApi(): OpenWeatherApi {
        return api
    }

    fun getWeatherPresenter(): WeatherPresenter {
        return presenter
    }

    fun getWeatherModel(): WeatherModel {
        return model
    }
}
