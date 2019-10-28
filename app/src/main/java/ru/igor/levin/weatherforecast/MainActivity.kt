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
import ru.igor.levin.weatherforecast.network.NetworkService
import ru.igor.levin.weatherforecast.network.OpenWeatherResponse
import ru.igor.levin.weatherforecast.network.OpenWeatherApiReactive
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val networkService = NetworkService.instance

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btGetWeather.setOnClickListener {
            showProgress(true)
            getWeather()
            //getWeatherReactive()
        }
    }

    private fun getWeatherReactive() {
        disposable =
            networkService.getOpenWeatherApiReactive()
                .getWeatherByCityId()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> showResult(result) },
                        { error -> showError(error.message) }
                    )
    }

    private fun getWeather() {
        networkService.getOpenWeatherApi()
            .getWeatherByCityId()
            .enqueue(object : Callback<OpenWeatherResponse.Result> {
                override fun onFailure(call: Call<OpenWeatherResponse.Result>, t: Throwable) {
                    showError(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<OpenWeatherResponse.Result>,
                    response: Response<OpenWeatherResponse.Result>
                ) {
                    showResult(response.body())
                }
            })
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

    private fun showResult(result: OpenWeatherResponse.Result?) {
        showProgress(false)
        Timber.d(result?.toString())
        textView.text = result?.toString()
    }

    private fun showError(message: String?) {
        showProgress(false)
        Timber.d(message)
        textView.text = message
    }
}
