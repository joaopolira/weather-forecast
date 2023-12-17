package com.meteorology.weatherforecast.client

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class HgForecastResponse(
    val date: String,
    val max: Int,
    val min: Int
)
