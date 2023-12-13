package com.meteorology.weatherforecast.data.db

import com.meteorology.weatherforecast.data.WeatherForecastRepository
import com.meteorology.weatherforecast.exception.PostgreSqlException
import com.meteorology.weatherforecast.model.Cities
import com.meteorology.weatherforecast.model.WeatherForecast
import com.meteorology.weatherforecast.model.WeatherForecastGroup
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Repository
class WeatherForecastRepositoryImpl(
    private val dao: WeatherForecastRepositoryDAO
) : WeatherForecastRepository {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun findByCity(city: String): WeatherForecastGroup {
        val weatherForecastList = try {
            dao.findByCity(city = city)
        } catch (ex: Exception) {
            logger.error("Action: findByCity - Error on database : ${ex.message}")
            throw PostgreSqlException("Error on database")
        }
        return WeatherForecastGroup.create(weatherForecastList = weatherForecastList)
    }

    override fun save(weatherForecast: WeatherForecast) {
        try {
            dao.save(weatherForecast)
        } catch (ex: Exception) {
            logger.error("error on database: ${ex.message}")
            throw PostgreSqlException("error on database")
        }
    }

    override fun findCities(): Cities {
        try {
            val cities = dao.findCities()
            return Cities.create(cities)
        } catch (ex: Exception) {
            logger.error("error on database: ${ex.message}")
            throw PostgreSqlException("error on database")
        }
    }
}