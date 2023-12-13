package com.meteorology.weatherforecast.data.db

import com.meteorology.weatherforecast.model.WeatherForecast
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface WeatherForecastRepositoryDAO : JpaRepository<WeatherForecast, Long> {
    fun findByCity(city: String): List<WeatherForecast>
    @Query(value = "SELECT city FROM Weatherforecast GROUP BY city", nativeQuery = true)
    fun findCities(): List<String>?
}