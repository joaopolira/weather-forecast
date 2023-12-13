package com.meteorology.weatherforecast.client

import com.meteorology.weatherforecast.configuration.FeignHGforecastConfiguration
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "hgForecastClient",
    url = "\${client.hgforecast.url}",
    configuration = [FeignHGforecastConfiguration::class]
)
interface HgForecastClient {

    companion object {
        private const val CITY_NAME = "city_name"
    }

    @GetMapping
    fun getWeatherForecastByCity(
        @RequestParam(CITY_NAME) city: String
    ): HGWeatherForecastResponse
}