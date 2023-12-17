package com.meteorology.weatherforecast.client

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.meteorology.weatherforecast.model.WeatherForecast

@JsonIgnoreProperties(ignoreUnknown = true)
data class HGWeatherForecastResponse(
    val results: HGWeatherForecastResultsResponse
) {

    companion object {
        private const val INT_TWO = 2
    }

    @get:JsonProperty(required = false)
    val max get() = results.forecast.first().max

    @get:JsonProperty(required = false)
    val min get() = results.forecast.first().min

    @get:JsonProperty(required = false)
    val average get() = max.plus(min).div(INT_TWO)

    fun toWeatherForecast(): WeatherForecast {
        return WeatherForecast(
            temperature = results.temp,
            description = results.description,
            city = results.city.lowercase(),
            humidity = results.humidity,
            rain = results.rain,
            sunrise = results.sunrise,
            sunset = results.sunset
        )
    }
}