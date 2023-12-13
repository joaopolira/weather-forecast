package com.meteorology.weatherforecast.service

import com.meteorology.weatherforecast.client.HgForecastClient
import com.meteorology.weatherforecast.data.WeatherForecastRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class UpdateTemperatureCityService(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val hgForecastClient: HgForecastClient
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    fun update() {
        val cities = weatherForecastRepository.findCities()
        if (cities.isNotEmpty) {
            cities.forEach {
                try {
                    val hgForecastClient = hgForecastClient.getWeatherForecastByCity(city = it)
                    val weatherForecast = hgForecastClient.toWeatherForecast()
                    weatherForecastRepository.save(weatherForecast = weatherForecast)
                } catch (ex: Exception) {
                    logger.error("error: ${ex.message}")
                }
            }
        }
        logger.info("finished update for ${cities.count} cities")
    }
}