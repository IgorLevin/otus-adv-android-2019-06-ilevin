package ru.igor.levin.weatherforecast

import android.app.Application
import android.content.Context
import ru.igor.levin.weatherforecast.data.WeatherRepositoryImpl
import ru.igor.levin.weatherforecast.data.network.NetworkService
import ru.igor.levin.weatherforecast.domain.interactor.WeatherInteractorImpl
import ru.igor.levin.weatherforecast.presentation.presenter.WeatherPresenter
import ru.igor.levin.weatherforecast.presentation.presenter.WeatherPresenterImpl

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
        NetworkService.instance().getOpenWeatherApi()
    }

    private val repository by lazy {
        WeatherRepositoryImpl(api)
    }

    private val interactor by lazy {
        WeatherInteractorImpl(repository)
    }

    private val presenter by lazy {
        WeatherPresenterImpl(interactor)
    }

    fun getWeatherPresenter(): WeatherPresenter {
        return presenter
    }

}
