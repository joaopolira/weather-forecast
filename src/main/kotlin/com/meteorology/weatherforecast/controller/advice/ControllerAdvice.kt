package com.meteorology.weatherforecast.controller.advice

import feign.FeignException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(FeignException::class)
    fun feignClientException(ex: FeignException): ResponseEntity<Any> {
        val statusCode = ex.status()
        val status = HttpStatus.valueOf(statusCode).name
        val errorResponse =
            ErrorResponse.create(
                status = status,
                statusCode = statusCode,
                message = ex.message ?: status
            )
        return ResponseEntity.status(statusCode).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handlerException(ex: Exception): ResponseEntity<Any> {
        val errorResponse =
            ErrorResponse.create(
                status = INTERNAL_SERVER_ERROR.name,
                statusCode = INTERNAL_SERVER_ERROR.ordinal,
                message = ex.message ?: INTERNAL_SERVER_ERROR.name
            )
        return ResponseEntity.internalServerError().body(errorResponse)
    }
}