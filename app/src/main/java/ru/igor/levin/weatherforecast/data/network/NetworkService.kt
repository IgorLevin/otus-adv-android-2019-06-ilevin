package ru.igor.levin.weatherforecast.data.network

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class NetworkService private constructor() {

    private val retrofit: Retrofit
    private val retrofitReactive: Retrofit

    companion object {

        private val LOCK = Any()
        private var instance: NetworkService? = null

        fun instance(): NetworkService {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = NetworkService()
                    }
                }
            }
            return instance!!
        }
    }

    init {
        val loggingInterceptor = HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message -> Timber.d(message) }
        )
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .certificatePinner(
                CertificatePinner.Builder()
                    .add("api.openweathermap.org", "sha256/0yOBPqOzhyp7U/418MLiLzURUneGendsdgs9G2+I2CA=")
                    .add("api.openweathermap.org", "sha256/klO23nT2ehFDXCfx3eHTDRESMz3asj1muO+4aIdjiuY=")
                    .add("api.openweathermap.org", "sha256/grX4Ta9HpZx6tSHkmCrvpApTQGo67CYDnvprLg5yRME=")
                    .build()
            )
            .build()

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()

        retrofitReactive = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()
    }

    fun getOpenWeatherApi(): OpenWeatherApi {
        return retrofit.create(OpenWeatherApi::class.java)
    }

    fun getOpenWeatherApiReactive(): OpenWeatherApiReactive {
        return retrofitReactive.create(OpenWeatherApiReactive::class.java)
    }

}