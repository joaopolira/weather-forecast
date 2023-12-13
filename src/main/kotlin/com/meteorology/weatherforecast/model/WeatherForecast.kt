package com.meteorology.weatherforecast.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(name = "weatherforecast")
class WeatherForecast(
    @field:Id
    @field:Column(name = "id")
    @field:GeneratedValue(strategy = IDENTITY)
    private var _id: Long? = null,

    @field:Column(name = "temperature")
    val temperature: Int,

    @field:Column(name = "date_time")
    val dateTime: LocalDateTime = LocalDateTime.now(),

    @field:Column(name = "description")
    val description: String,

    @field:Column(name = "city")
    val city: String,

    @field:Column(name = "humidity")
    val humidity: Int,

    @field:Column(name = "rain")
    val rain: Double,

    @field:Column(name = "sunrise")
    val sunrise: String,

    @field:Column(name = "sunset")
    val sunset: String
) {
    fun isUpToDate(): Boolean {
        val dateTimeNow = LocalDate.now()
        return this.dateTime.toLocalDate().equals(dateTimeNow)
    }

    val id get() = this._id ?: throw Exception("Entity not persisted")
}