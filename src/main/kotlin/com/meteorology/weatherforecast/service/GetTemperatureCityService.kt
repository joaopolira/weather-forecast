package com.meteorology.weatherforecast.service

import com.meteorology.weatherforecast.client.HgForecastClient
import com.meteorology.weatherforecast.data.WeatherForecastRepository
import com.meteorology.weatherforecast.model.WeatherForecast
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GetTemperatureCityService(
    private val hgForecastClient: HgForecastClient,
    private val weatherForecastRepository: WeatherForecastRepository
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun execute(city: String): WeatherForecast {
        val weatherForecastGroup = weatherForecastRepository.findByCity(city = city)
        if (weatherForecastGroup.upToDateForecastIsPresent) {
            logger.info("finished")
            return weatherForecastGroup.upToDateForecast
        }
        val hgForecastResponse = hgForecastClient.getWeatherForecastByCity(city = city)
        val weatherForecast = hgForecastResponse.toWeatherForecast()
        weatherForecastRepository.save(weatherForecast = weatherForecast)
        logger.info("finished")
        return weatherForecast
    }
}