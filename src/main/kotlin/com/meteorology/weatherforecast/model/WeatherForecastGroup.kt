package com.meteorology.weatherforecast.model

import java.util.Optional

class WeatherForecastGroup(
    private val weatherForecastList: List<WeatherForecast>
) {
    companion object {
        fun create(
            weatherForecastList: List<WeatherForecast>
        ): WeatherForecastGroup {
            return WeatherForecastGroup(weatherForecastList = weatherForecastList)
        }
    }

    private val lastForecast: Optional<WeatherForecast> get() {
        val weatherForecast = weatherForecastList.maxByOrNull { it.dateTime } ?: return Optional.empty()
        return Optional.of(weatherForecast)
    }

    val upToDateForecastIsPresent: Boolean get() {
        if (!lastForecast.isPresent) return false
        return lastForecast.get().isUpToDate()
    }

    val upToDateForecast: WeatherForecast get() = lastForecast.get()
}