package com.meteorology.weatherforecast.data

import com.meteorology.weatherforecast.model.Cities
import com.meteorology.weatherforecast.model.WeatherForecast
import com.meteorology.weatherforecast.model.WeatherForecastGroup

interface WeatherForecastRepository {
    fun findByCity(city: String): WeatherForecastGroup
    fun save(weatherForecast: WeatherForecast)
    fun findCities(): Cities
}