package ru.igor.levin.weatherforecast

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.igor.levin.weatherforecast.model.network.NetworkService
import ru.igor.levin.weatherforecast.model.network.OpenWeatherResponse
import ru.igor.levin.weatherforecast.presenter.WeatherPresenter
import ru.igor.levin.weatherforecast.view.WeatherView
import timber.log.Timber

class MainActivity : AppCompatActivity(), WeatherView {

    private val serviceLocator by lazy {
        ServiceLocator.instance(this)
    }
    private lateinit var presenter: WeatherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = serviceLocator.getWeatherPresenter()
        setContentView(R.layout.activity_main)

        btGetWeather.setOnClickListener {
            presenter.getWeather()
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

    override fun showWind(deg: String) {
        tvWind.text = deg
    }

    override fun showPrecipitation(mm: String) {
        tvPrecipitation.visibility = View.VISIBLE
        tvPrecipitation.text = mm
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

    override fun showCloudness(percent: String) {
        tvCloudness.text = percent
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
