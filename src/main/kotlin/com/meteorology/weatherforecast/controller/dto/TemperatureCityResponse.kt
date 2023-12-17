package com.meteorology.weatherforecast.controller.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.meteorology.weatherforecast.model.WeatherForecast

@JsonInclude(JsonInclude.Include.NON_NULL)
class TemperatureCityResponse(
    val temperature: Int,
    val average: Int,
    val max: Int,
    val min: Int,
    val datetime: String,
    val description: String,
    val city: String,
    val humidity: Int,
    val sunrise: String,
    val sunset: String
) {
    companion object {
        fun create(weatherForecast: WeatherForecast): TemperatureCityResponse {
            return TemperatureCityResponse(
                temperature = weatherForecast.temperature,
                average = weatherForecast.average!!,
                max = weatherForecast.max!!,
                min = weatherForecast.min!!,
                datetime = weatherForecast.dateTime.toString(),
                description = weatherForecast.description,
                city = weatherForecast.city,
                humidity = weatherForecast.humidity,
                sunrise = weatherForecast.sunrise,
                sunset = weatherForecast.sunset
            )
        }
    }
}