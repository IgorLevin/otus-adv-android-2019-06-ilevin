package ru.igor.levin.weatherforecast.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface OpenWeatherApi : OpenWeatherApiBase<Call<OpenWeatherResponse.Result>> {

    companion object {
        fun create(): OpenWeatherApi {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(OpenWeatherApi::class.java)
        }
    }
}