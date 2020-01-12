package ru.igor.levin.weatherforecast.presentation.presenter

import ru.igor.levin.weatherforecast.domain.WeatherLoadingState
import ru.igor.levin.weatherforecast.domain.entity.Weather
import ru.igor.levin.weatherforecast.domain.interactor.WeatherInteractor
import ru.igor.levin.weatherforecast.presentation.view.WeatherView
import java.lang.ref.WeakReference

class WeatherPresenterImpl(
    private val weatherInteractor: WeatherInteractor
)
    : WeatherPresenter, WeatherInteractor.Listener
{
    private var viewRef: WeakReference<WeatherView>? = null
    private val view: WeatherView?
        get() = viewRef?.get()

    companion object {
        private const val HPA_IN_MMHG: Double = 1.3332239
    }

    override fun onViewActivated(view: WeatherView) {
        viewRef = WeakReference(view)
        weatherInteractor.subscribe(this)
    }

    override fun onViewDeactivated() {
        viewRef?.clear()
        weatherInteractor.unsubscribe()
    }

    override fun onViewDestroyed() {
        viewRef?.clear()
        weatherInteractor.unsubscribe()
    }

    override fun updateWeather() {
        weatherInteractor.updateWeather()
    }

    override fun onWeatherLoadingStateChanged(state: WeatherLoadingState<Weather>) {
        view?.let { view ->
            when (state) {
                is WeatherLoadingState.Success -> {
                    view.hideProgress()
                    showWeather(view, state.data!!)
                }
                is WeatherLoadingState.Error -> {
                    view.hideProgress()
                    view.showError(state.msg)
                }
                is WeatherLoadingState.Loading -> {
                    view.showProgress()
                }
            }
        }
    }

    private fun showWeather(view: WeatherView, weather: Weather) {
        view.showWeatherScreen()
        view.showCity(weather.city ?: " - ")
        view.showDescription(weather.description ?: " - ")
        view.showClouds(weather.cloudness ?: " - ")
        view.showTemperature(weather.temperature ?: " - ")
        view.showHumidity(weather.humidity ?: " - ")
        weather.pressure?.apply {
            val pressure = (toFloat()/ HPA_IN_MMHG).toInt()
            view.showPressure(pressure.toString())
        }
        if (weather.snow != null || weather.rain != null) {
            view.showPrecipitation(if (weather.snow.isNullOrEmpty()) weather.rain!! else weather.snow!!)
        } else {
            view.hidePrecipitation()
        }
        if (!weather.windDirection.isNullOrEmpty()) {
            view.showWind("${weather.windSpeed} [${weather.windDirection}°]")
        }
        if (weather.cloudness != null) {
            view.showClouds(weather.cloudness!!)
        }
    }
}