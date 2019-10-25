package ru.igor.levin.weatherforecast.network

import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
const val APP_KEY: String = "2cf8752567a9645153f7dd316311fcbe" // API key
const val DEFAULT_CITY_ID: String = "524901" // Moscow
const val DEFAULT_CITY_NAME: String = "Moscow" // Moscow
const val DEFAULT_COUNTRY_CODE_ISO_3166: String = "ru"
const val DEFAULT_LANGUAGE: String = "ru"

interface OpenWeatherApiBase<T> {

    @GET("weather")
    fun getWeatherByCityId(
        @Query("id") cityId: String = DEFAULT_CITY_ID,
        @Query("appId") appId: String = APP_KEY,
        @Query("lang") language: String = DEFAULT_LANGUAGE,
        @Query("units") units: String = "metric"): T

    @GET("weather")
    fun getWeatherByCityName(
        @Query("q") cityName: String = DEFAULT_CITY_NAME,
        @Query("appId") appId: String = APP_KEY,
        @Query("lang") language: String = DEFAULT_LANGUAGE,
        @Query("units") units: String = "metric"): T

    @GET("weather")
    fun getWeatherByCountryCityName(
        @Query("q") city: City = City(DEFAULT_CITY_NAME, DEFAULT_COUNTRY_CODE_ISO_3166),
        @Query("appId") appId: String = APP_KEY,
        @Query("lang") language: String = DEFAULT_LANGUAGE,
        @Query("units") units: String = "metric"): T

    @GET("weather")
    fun getWeatherByCoordinates(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appId") appId: String = APP_KEY,
        @Query("lang") language: String = DEFAULT_LANGUAGE,
        @Query("units") units: String = "metric"): T

    data class City(val cityName: String, val countryCodeIso3166: String) {
        override fun toString(): String {
            return "$cityName,$countryCodeIso3166"
        }
    }
}