package com.meteorology.weatherforecast.client

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class HGWeatherForecastResultsResponse(
    val temp: Int,
    val description: String,
    @JsonProperty("city_name")
    val city: String,
    val humidity: Int,
    val rain: Double,
    val sunrise: String,
    val sunset: String
)