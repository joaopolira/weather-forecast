package com.meteorology.weatherforecast.controller.advice

class ErrorResponse(
    statusCode: Int,
    status: String,
    message: String
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