package ru.igor.levin.weatherforecast.presentation.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.igor.levin.weatherforecast.R
import ru.igor.levin.weatherforecast.WeatherApplication
import ru.igor.levin.weatherforecast.presentation.presenter.WeatherPresenter
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(), WeatherView {

    @Inject
    lateinit var presenter: WeatherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as WeatherApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btUpdateWeather.setOnClickListener {
            presenter.updateWeather()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewActivated(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.onViewDeactivated()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun showWeatherScreen() {
        svWeatherDetails.visibility = View.VISIBLE
        tvErrorInfo.visibility = View.GONE
    }

    override fun showTemperature(degrees: String) {
        tvTemperature.text = degrees
    }

    override fun showHumidity(percent: String) {
        tvHumidity.text = percent
    }

    override fun showPressure(mm: String) {
        tvPressure.text = mm
    }

    override fun showWind(direction: String) {
        tvWind.text = direction
    }

    override fun showPrecipitation(levelInMm: String) {
        tvPrecipitation.visibility = View.VISIBLE
        tvPrecipitation.text = levelInMm
    }

    override fun hidePrecipitation() {
        tvPrecipitation.visibility = View.GONE
    }

    override fun showDescription(info: String) {
        tvDescription.text = info
    }

    override fun showCity(name: String) {
        tvCity.text = name
    }

    override fun showClouds(percent: String) {
        tvClouds.text = percent
    }

    override fun showError(msg: String?) {
        Timber.d(msg)
        svWeatherDetails.visibility = View.GONE
        tvErrorInfo.visibility = View.VISIBLE
        tvErrorInfo.text = msg
    }

    override fun showProgress() {
        svWeatherDetails.visibility = View.GONE
        tvErrorInfo.visibility = View.GONE

        progress.visibility = View.VISIBLE
        progress.startProgress()
    }

    override fun hideProgress() {
        progress.stopProgress()
        progress.visibility = View.GONE
    }
}
