package ru.igor.levin.weatherforecast.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiReactive {

    @GET("weather")
    fun getWeatherByCityId(
        @Query("id") cityId: String = DEFAULT_CITY_ID,
        @Query("appId") appId: String = APP_KEY,
        @Query("lang") language: String = DEFAULT_LANGUAGE,
        @Query("units") units: String = "metric"): Observable<OpenWeatherResponse.Result>

    @GET("weather")
    fun getWeatherByCityName(
        @Query("q") cityName: String = DEFAULT_CITY_NAME,
        @Query("appId") appId: String = APP_KEY,
        @Query("lang") language: String = DEFAULT_LANGUAGE,
        @Query("units") units: String = "metric"): Observable<OpenWeatherResponse.Result>

    @GET("weather")
    fun getWeatherByCountryCityName(
        @Query("q") city: City = City(DEFAULT_CITY_NAME, DEFAULT_COUNTRY_CODE_ISO_3166),
        @Query("appId") appId: String = APP_KEY,
        @Query("lang") language: String = DEFAULT_LANGUAGE,
        @Query("units") units: String = "metric"): Observable<OpenWeatherResponse.Result>

    @GET("weather")
    fun getWeatherByCoordinates(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appId") appId: String = APP_KEY,
        @Query("lang") language: String = DEFAULT_LANGUAGE,
        @Query("units") units: String = "metric"): Observable<OpenWeatherResponse.Result>
}