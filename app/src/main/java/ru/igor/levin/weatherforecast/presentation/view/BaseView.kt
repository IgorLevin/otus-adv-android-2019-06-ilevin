package ru.igor.levin.weatherforecast.presentation.view

interface BaseView {
    fun showProgress()
    fun hideProgress()

    fun showError(msg: String?)
}