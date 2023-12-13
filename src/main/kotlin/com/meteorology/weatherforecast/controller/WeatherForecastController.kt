package com.meteorology.weatherforecast.controller

import com.meteorology.weatherforecast.controller.dto.TemperatureCityResponse
import com.meteorology.weatherforecast.service.GetTemperatureCityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/weather/forecast/v1/temperature")
class WeatherForecastController(
    private val getTemperatureCityService: GetTemperatureCityService
) {

    @GetMapping("/city/{city}")
    fun getTemperature(
        @PathVariable("city") city: String
    ): ResponseEntity<Any> {
        val weatherForecast = getTemperatureCityService.execute(city = city.lowercase())
        val temperatureCity = TemperatureCityResponse.create(weatherForecast)
        return ResponseEntity.ok().body(temperatureCity)
    }
}