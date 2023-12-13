package com.meteorology.weatherforecast.client

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.meteorology.weatherforecast.model.WeatherForecast

@JsonIgnoreProperties(ignoreUnknown = true)
data class HGWeatherForecastResponse(
    val results: HGWeatherForecastResultsResponse
) {
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