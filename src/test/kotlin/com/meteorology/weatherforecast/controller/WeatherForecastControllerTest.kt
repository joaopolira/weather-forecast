package com.meteorology.weatherforecast.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.meteorology.weatherforecast.StubBuilder
import com.meteorology.weatherforecast.controller.dto.TemperatureCityResponse
import com.meteorology.weatherforecast.data.db.WeatherForecastRepositoryDAO
import com.meteorology.weatherforecast.tools.PostgresSQLContainerDefinition
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.core.io.Resource
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
class WeatherForecastControllerTest : PostgresSQLContainerDefinition {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var dao: WeatherForecastRepositoryDAO

    @Value("classpath:/mass/weather_forecast_campinas.json")
    private lateinit var happyReponse: Resource

    @BeforeEach
    fun setup() {
        dao.deleteAll()
    }

    @Test
    fun `given that the request was made successfully, must return status code 200`() {
        val city = "campinas"

        StubBuilder.stubFor2XX(
            param = city,
            pathUrl = "/weather",
            response = happyReponse.file.readText(),
            statusCode = 200
        )

        val request =
            get("/weather/forecast/v1/temperature/city/{city}", city)
                .contentType(APPLICATION_JSON)

        val response = mockMvc.perform(request)
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString
            .run {
                return@run objectMapper.readValue(this, TemperatureCityResponse::class.java)
            }

        val weatherForecast = dao.findByCity(city = city)
        assertAll(
            { assertEquals(response.city, weatherForecast.first().city) },
            { assertEquals(response.temperature, weatherForecast.first().temperature) }
        )
    }

    @Test
    fun `given that the request was not made successfully, it must return a status 5XX`() {
        val city = "campinas"

        StubBuilder.stubForWithError5XX(
            param = city,
            pathUrl = "/weather",
            statusCode = 500
        )

        val request =
            get("/weather/forecast/v1/temperature/city/{city}", city)
                .contentType(APPLICATION_JSON)

        val statusCode = mockMvc.perform(request)
            .andReturn()
            .response
            .status

        val weatherForecast = dao.findByCity(city = city)
        assertAll(
            { assertEquals(0, weatherForecast.size) },
            { assertEquals(500, statusCode) }
        )
    }


    @Test
    fun `given that the request was not made successfully due to client error, it must return a status 4XX`() {
        val city = "campinas"

        StubBuilder.stubForWithError4XX(
            param = city,
            pathUrl = "/weather",
            statusCode = 400
        )

        val request =
            get("/weather/forecast/v1/temperature/city/{city}", city)
                .contentType(APPLICATION_JSON)


        val statusCode = mockMvc.perform(request)
                .andReturn()
                .response
                .status


        val requestHealth =
            get("/actuator/health", city)
                .contentType(APPLICATION_JSON)

        val resp = mockMvc.perform(requestHealth)
            .andReturn()
            .response

        val weatherForecast = dao.findByCity(city = city)
        assertAll(
            { assertEquals(0, weatherForecast.size) },
            { assertEquals(400, statusCode) }
        )
    }
}