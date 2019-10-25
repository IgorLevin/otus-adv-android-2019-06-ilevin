package ru.igor.levin.weatherforecast

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.igor.levin.weatherforecast.network.OpenWeatherResponse
import ru.igor.levin.weatherforecast.network.OpenWeatherApiBase
import ru.igor.levin.weatherforecast.network.OpenWeatherApiReactive

class MainActivity : AppCompatActivity() {

    private val weatherApi by lazy {
        OpenWeatherApiReactive.create()
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
            weatherApi.getWeatherByCityId()
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
