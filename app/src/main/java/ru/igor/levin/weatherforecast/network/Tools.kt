package ru.igor.levin.weatherforecast.network


const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
const val APP_KEY: String = "2cf8752567a9645153f7dd316311fcbe" // API key
const val DEFAULT_CITY_ID: String = "524901" // Moscow
const val DEFAULT_CITY_NAME: String = "Moscow" // Moscow
const val DEFAULT_COUNTRY_CODE_ISO_3166: String = "ru"
const val DEFAULT_LANGUAGE: String = "ru"

data class City(val cityName: String, val countryCodeIso3166: String) {
    override fun toString(): String {
        return "$cityName,$countryCodeIso3166"
    }
}