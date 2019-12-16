package ru.igor.levin.weatherforecast.view

interface BasicView {
    fun showProgress()
    fun hideProgress()

    fun showError(msg: String?)
}