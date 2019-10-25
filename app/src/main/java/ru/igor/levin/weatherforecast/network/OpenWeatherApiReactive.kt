package ru.igor.levin.weatherforecast.network

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface OpenWeatherApiReactive: OpenWeatherApiBase<Observable<OpenWeatherResponse.Result>> {

    companion object {
        fun create() : OpenWeatherApiReactive {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(OpenWeatherApiReactive::class.java)
        }
    }
}