package ru.igor.levin.weatherforecast.presenter

import ru.igor.levin.weatherforecast.model.WeatherModelData
import ru.igor.levin.weatherforecast.model.WeatherModelResponse
import ru.igor.levin.weatherforecast.model.WeatherRepository
import ru.igor.levin.weatherforecast.view.WeatherView
import java.lang.ref.WeakReference
import java.util.*

private const val HPA_IN_MMHG: Double = 1.3332239

class WeatherPresenterImpl(private val repository: WeatherRepository) : WeatherPresenter {

    private var viewRef: WeakReference<WeatherView>? = null
    private var modelResponse: WeatherModelResponse<WeatherModelData>? = null

    override fun onViewActivated(view: WeatherView) {
        viewRef = WeakReference(view)
        modelResponse?.apply {
            onModelDataUpdated(this)
        }
    }

    override fun onViewDeactivated() {
        viewRef?.clear()
    }

    override fun onViewDestroyed() {
        viewRef?.clear()
        repository.cancelRequest()
    }

    override fun getWeather() {
        modelResponse = null
        repository.getWeather(
            Observer { _, arg ->
                modelResponse = arg as WeatherModelResponse<WeatherModelData>
                onModelDataUpdated(modelResponse!!)
            }
        )
    }

    private fun onModelDataUpdated(modelResponse: WeatherModelResponse<WeatherModelData>) {
        when (modelResponse) {
            is WeatherModelResponse.Success -> {
                getView()?.hideProgress()
                showWeather(modelResponse.data!!)
            }
            is WeatherModelResponse.Error -> {
                getView()?.hideProgress()
                getView()?.showError(modelResponse.msg)
            }
            is WeatherModelResponse.Loading -> {
                getView()?.showProgress()
            }
        }
    }

    private fun showWeather(data: WeatherModelData) {
        getView()?.apply {
            showWeatherScreen()

            showCity(data.city ?: " - ")
            showDescription(data.description ?: " - ")
            showCloudness(data.cloudness ?: " - ")
            showTemperature(data.temperature ?: " - ")
            showHumidity(data.humidity ?: " - ")
            data.pressure?.apply {
                val pressure = toFloat()/ HPA_IN_MMHG
                showPressure(pressure.toString())
            }
            if (data.snow != null || data.rain != null) {
                showPrecipitation(if (data.snow.isNullOrEmpty()) data.rain!! else data.snow!!)
            }
            if (!data.windDirection.isNullOrEmpty()) {
                showWind("${data.windSpeed} [${data.windDirection}°]")
            }
            if (data.cloudness != null) {
                showCloudness(data.cloudness!!)
            }
        }
    }

    private fun getView(): WeatherView? {
        return viewRef?.get()
    }
}