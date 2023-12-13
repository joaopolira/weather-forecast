package com.meteorology.weatherforecast.service


import com.meteorology.weatherforecast.client.HgForecastClient
import com.meteorology.weatherforecast.data.WeatherForecastRepository
import com.meteorology.weatherforecast.tools.INT_ONE
import com.meteorology.weatherforecast.tools.INT_ZERO
import com.meteorology.weatherforecast.tools.createHGWeatherForecastResponse
import com.meteorology.weatherforecast.tools.createWeatherForecastGroupRepositoryResponse
import com.meteorology.weatherforecast.tools.lastLocalDate
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired

@ExtendWith(MockitoExtension::class)
class GetTemperatureCityServiceTest {

    @Mock
    private lateinit var hgForecastClient: HgForecastClient

    @Mock
    private lateinit var weatherForecastRepository: WeatherForecastRepository

    @InjectMocks
    @Autowired
    private lateinit var getTemperatureCityService: GetTemperatureCityService

    @Test
    fun `given there is weather forecast data in the database, must not call the client`() {

        val city = "campinas"

        val weatherForecastGroupResponse = createWeatherForecastGroupRepositoryResponse()
        `when`(weatherForecastRepository.findByCity(city = city))
            .thenReturn(weatherForecastGroupResponse)

        val weatherForecast = getTemperatureCityService.execute(city = city)

        assertEquals(lastLocalDate.toLocalDate(), weatherForecast.dateTime.toLocalDate())
        verify(hgForecastClient, times(INT_ZERO)).getWeatherForecastByCity(city = city)
    }

    @Test
    fun `given that there is no weather forecast data in the database, must call the client`() {
        val city = "campinas"

        val weatherForecastGroupResponse =
            createWeatherForecastGroupRepositoryResponse(
                LocalDateTime.of(2023, 12, 4, 20, 23, 10, 1),
                LocalDateTime.of(2023, 12, 3, 20, 23, 10, 1),
                LocalDateTime.of(2023, 12, 2, 20, 23, 10, 1),
            )
        `when`(weatherForecastRepository.findByCity(city = city))
            .thenReturn(weatherForecastGroupResponse)

        val hgResponse = createHGWeatherForecastResponse()
        `when`(hgForecastClient.getWeatherForecastByCity(city = city))
            .thenReturn(hgResponse)

        val weatherForecast = getTemperatureCityService.execute(city = city)

        assertEquals(hgResponse.toWeatherForecast().city, weatherForecast.city)
        assertEquals(hgResponse.toWeatherForecast().temperature, weatherForecast.temperature)
        verify(weatherForecastRepository, times(INT_ONE)).findByCity(city = city)
        verify(weatherForecastRepository, times(INT_ONE)).save(weatherForecast = weatherForecast)
    }
}