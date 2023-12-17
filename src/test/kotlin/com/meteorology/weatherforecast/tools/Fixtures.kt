package com.meteorology.weatherforecast.tools

import com.meteorology.weatherforecast.client.HGWeatherForecastResponse
import com.meteorology.weatherforecast.client.HGWeatherForecastResultsResponse
import com.meteorology.weatherforecast.client.HgForecastResponse
import com.meteorology.weatherforecast.model.WeatherForecast
import com.meteorology.weatherforecast.model.WeatherForecastGroup
import java.time.LocalDateTime
import kotlin.math.max

internal const val INT_ZERO = 0
internal const val INT_ONE = 1

internal fun createHGWeatherForecastResponse(): HGWeatherForecastResponse {
    val results =
        HGWeatherForecastResultsResponse(
            temp = 55,
            description = "Parcialmente nublado",
            city = "campinas",
            humidity = 50,
            rain = 0.0,
            sunrise = "5:10 am",
            sunset = "6:25 pm",
            forecast = listOf(
                HgForecastResponse(
                    date = "17/12",
                    max = 35,
                    min = 23
                )
            )
        )
    return HGWeatherForecastResponse(results = results)
}

internal fun createWeatherForecastRepositoryResponse(): WeatherForecast {
    return WeatherForecast(
        _id = 1L,
        temperature = 55,
        dateTime = LocalDateTime.now(),
        description = "Parcialmente nublado",
        city = "campinas",
        humidity = 50,
        rain = 0.0,
        sunrise = "5:10 am",
        sunset = "6:25 pm"
    )
}

internal fun createWeatherForecastRepositoryResponse(
    localDateTime: LocalDateTime
): WeatherForecast {
    return WeatherForecast(
        _id = 1L,
        temperature = 55,
        dateTime = localDateTime,
        description = "Parcialmente nublado",
        city = "campinas",
        humidity = 50,
        rain = 0.0,
        sunrise = "5:10 am",
        sunset = "6:25 pm"
    )
}

internal val lastLocalDate: LocalDateTime = LocalDateTime.now()
internal val midLocalDate: LocalDateTime = LocalDateTime.of(2023, 12, 4, 18, 44, 55, 1)
internal val firstLocalDate: LocalDateTime = LocalDateTime.of(2023, 12, 3, 17, 44, 55, 1)

internal fun createWeatherForecastGroupRepositoryResponse(): WeatherForecastGroup {
    val weatherForecastList = listOf(
        createWeatherForecastRepositoryResponse(localDateTime = firstLocalDate),
        createWeatherForecastRepositoryResponse(localDateTime = midLocalDate),
        createWeatherForecastRepositoryResponse(localDateTime = lastLocalDate)
    )
    return WeatherForecastGroup(weatherForecastList = weatherForecastList)
}

internal fun createWeatherForecastGroupRepositoryResponse(
    vararg localDateTimes: LocalDateTime
): WeatherForecastGroup {
    val weatherForecastList = mutableListOf<WeatherForecast>()
    localDateTimes.forEach {
        weatherForecastList.add(
            createWeatherForecastRepositoryResponse(it)
        )
    }
    return WeatherForecastGroup(weatherForecastList = weatherForecastList)
}

