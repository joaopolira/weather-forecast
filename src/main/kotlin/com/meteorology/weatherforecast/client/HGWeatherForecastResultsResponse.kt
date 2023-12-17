package com.meteorology.weatherforecast.client

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class HGWeatherForecastResultsResponse(
    val temp: Int,
    val description: String,
    @JsonProperty("city_name")
    val city: String,
    val humidity: Int,
    val rain: Double,
    val sunrise: String,
    val sunset: String,
    val forecast: List<HgForecastResponse>
)