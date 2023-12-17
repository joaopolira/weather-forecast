package com.meteorology.weatherforecast.model

import java.time.LocalDate
import java.util.*

class WeatherForecastGroup(
    private val weatherForecastList: List<WeatherForecast>
) {
    companion object {
        private const val SEVEN_DAYS = 7L
        fun create(
            weatherForecastList: List<WeatherForecast>
        ): WeatherForecastGroup {
            return WeatherForecastGroup(weatherForecastList = weatherForecastList)
        }
    }

    init {
        if (upToDateForecastIsPresent) {
            fetchtempratureRangeOfSevenDaysBefore()
            setTemperatureCalculator()
        }
    }

    private lateinit var tempratureRangeOfSevenDaysBefore: List<Int>

    var average: Int? = null

    var min: Int? = null

    var max: Int? = null

    private fun fetchtempratureRangeOfSevenDaysBefore() {
        tempratureRangeOfSevenDaysBefore = weatherForecastList
            .filter {
                val localDateNow = LocalDate.now()
                val localDateMinusSevenDays = localDateNow.minusDays(SEVEN_DAYS)
                localDateMinusSevenDays <= it.dateTime.toLocalDate() || localDateNow >= it.dateTime.toLocalDate()
            }
            .map { it.temperature }
    }

    private fun setTemperatureCalculator() {
        average = tempratureRangeOfSevenDaysBefore.sum().div(tempratureRangeOfSevenDaysBefore.size)
        min = tempratureRangeOfSevenDaysBefore.min()
        max = tempratureRangeOfSevenDaysBefore.max()
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