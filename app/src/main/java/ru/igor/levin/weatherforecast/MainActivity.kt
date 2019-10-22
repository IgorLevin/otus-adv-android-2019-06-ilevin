package ru.igor.levin.weatherforecast

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.igor.levin.weatherforecast.network.OpenWeatherResponse
import ru.igor.levin.weatherforecast.network.OpenWeatherApiService

class MainActivity : AppCompatActivity() {

    private val appId: String = "2cf8752567a9645153f7dd316311fcbe" // API key
    private val cityId: String = "524901" // Moscow

    private val weatherApi by lazy {
        OpenWeatherApiService.create()
    }

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btGetWeather.setOnClickListener {
            showProgress(true)
            getWeather()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun getWeather() {
        disposable =
            weatherApi.getWeather(cityId, appId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> showResult(result) },
                    { error -> showError(error.message) }
                )
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            progress.visibility = View.VISIBLE
            progress.startProgress()
        } else {
            progress.stopProgress()
            progress.visibility = View.GONE
        }
    }

    private fun showResult(result: OpenWeatherResponse.Result) {
        showProgress(false)
        textView.text = result.toString()
    }

    private fun showError(message: String?) {
        showProgress(false)
        textView.text = message
    }
}
