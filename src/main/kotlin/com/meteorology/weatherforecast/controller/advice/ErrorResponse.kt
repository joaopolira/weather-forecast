package com.meteorology.weatherforecast.controller.advice

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class ErrorResponse(
    val statusCode: Int,
    val status: String,
    val message: String
) {
    companion object {
        fun create(
            statusCode: Int,
            status: String,
            message: String
        ): ErrorResponse {
            return ErrorResponse(
                statusCode = statusCode,
                status = status,
                message = message
            )
        }
    }
}