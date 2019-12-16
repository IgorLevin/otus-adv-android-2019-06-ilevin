package ru.igor.levin.weatherforecast

import android.app.Application
import android.content.Context
import ru.igor.levin.weatherforecast.model.WeatherModel
import ru.igor.levin.weatherforecast.model.WeatherRepository
import ru.igor.levin.weatherforecast.model.network.NetworkService
import ru.igor.levin.weatherforecast.model.network.OpenWeatherApi
import ru.igor.levin.weatherforecast.presenter.WeatherPresenter
import ru.igor.levin.weatherforecast.presenter.WeatherPresenterImpl

interface ServiceLocator {

    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null

        fun instance(context: Context): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator(app = context.applicationContext as Application)
                }
                return instance!!
            }
        }
    }

    fun getWeatherApi(): OpenWeatherApi

    fun getWeatherPresenter(): WeatherPresenter

    fun getWeatherModel(): WeatherModel
}

/**
 * default implementation of ServiceLocator that uses production endpoints.
 */
open class DefaultServiceLocator(val app: Application) : ServiceLocator {

    private val api by lazy {
        NetworkService.instance.getOpenWeatherApi()
    }

    private val model by lazy {
        WeatherRepository(api)
    }

    private val presenter by lazy {
        WeatherPresenterImpl(model)
    }

    override fun getWeatherApi(): OpenWeatherApi {
        return api
    }

    override fun getWeatherPresenter(): WeatherPresenter {
        return presenter
    }

    override fun getWeatherModel(): WeatherModel {
        return model
    }
}