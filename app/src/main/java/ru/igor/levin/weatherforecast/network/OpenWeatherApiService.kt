package ru.igor.levin.weatherforecast.network

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
//const val BASE_URL = "https://aaaaaapi.openweathermap.org/data/2.5/"

interface OpenWeatherApiService {

    @GET("weather")
    fun getWeather(@Query("id") cityId: String, @Query("appId") appId: String, @Query("units") units: String = "metric"): Observable<OpenWeatherResponse.Result>

    companion object {
        fun create(): OpenWeatherApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(OpenWeatherApiService::class.java)
        }
    }
}