package ru.igor.levin.weatherforecast.view

interface BaseView {
    fun showProgress()
    fun hideProgress()

    fun showError(msg: String?)
}